package com.campusincident.manager;

import com.campusincident.model.Incident;
import com.campusincident.observer.*;
import com.campusincident.strategy.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Shared coordinator for Observer subscriptions and runtime Strategy selection. */
public class SubscriptionManager {
    public enum Role {
        LAB_TECHNICIAN("Laboratory Technician", "Alex (Lab Technician)"),
        IT_SUPPORT("IT Support Staff", "Jordan (IT Support)"),
        DEPARTMENT_COORDINATOR("Department Coordinator", "Taylor (Coordinator)");

        public final String label;
        public final String recipient;
        Role(String label, String recipient) {
            this.label = label;
            this.recipient = recipient;
        }
        @Override public String toString() { return label; }
    }

    public enum Channel { EMAIL, SMS, IN_APP }

    private final Map<Role, IncidentObserver> observers = new EnumMap<>(Role.class);
    private final Map<Role, NotificationContext> contexts = new EnumMap<>(Role.class);
    private final Map<Role, Channel> channels = new EnumMap<>(Role.class);
    private final Map<String, EnumSet<Role>> subscriptions = new HashMap<>();
    private final List<String> notificationLog = new ArrayList<>();
    private Runnable onNotificationsChanged = () -> {};

    public SubscriptionManager() {
        for (Role role : Role.values()) {
            IncidentObserver observer = switch (role) {
                case LAB_TECHNICIAN -> new LabTechnicianObserver(role.recipient);
                case IT_SUPPORT -> new ITSupportObserver(role.recipient);
                case DEPARTMENT_COORDINATOR -> new DepartmentCoordinatorObserver(role.recipient);
            };
            NotificationContext context = new NotificationContext(strategyFor(role, Channel.IN_APP));
            setObserverContext(observer, context);
            observers.put(role, observer);
            contexts.put(role, context);
            channels.put(role, Channel.IN_APP);
        }
    }

    private static void setObserverContext(IncidentObserver observer, NotificationContext context) {
        if (observer instanceof LabTechnicianObserver lab) lab.setNotificationContext(context);
        else if (observer instanceof ITSupportObserver it) it.setNotificationContext(context);
        else if (observer instanceof DepartmentCoordinatorObserver coordinator) coordinator.setNotificationContext(context);
    }

    private NotificationStrategy strategyFor(Role role, Channel channel) {
        NotificationStrategy original = switch (channel) {
            case EMAIL -> new EmailNotificationStrategy();
            case SMS -> new SMSNotificationStrategy();
            case IN_APP -> new InAppNotificationStrategy();
        };
        // This decorator preserves the original Strategy behavior and also makes
        // simulated notifications visible in the Swing UI.
        return (recipient, message) -> {
            original.sendNotification(recipient, message);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            notificationLog.add(timestamp + " | " + channel + " | " + role.label
                    + " | " + recipient + "\n" + message);
            onNotificationsChanged.run();
        };
    }

    public void setNotificationsChangedListener(Runnable listener) {
        onNotificationsChanged = Objects.requireNonNull(listener);
    }

    public void setChannel(Role role, Channel channel) {
        Objects.requireNonNull(role);
        Objects.requireNonNull(channel);
        contexts.get(role).setStrategy(strategyFor(role, channel));
        channels.put(role, channel);
    }

    public Channel getChannel(Role role) { return channels.get(role); }

    public boolean isSubscribed(Incident incident, Role role) {
        return subscriptions.getOrDefault(incident.getIncidentId(), EnumSet.noneOf(Role.class)).contains(role);
    }

    public void setSubscribed(Incident incident, Role role, boolean subscribed) {
        Objects.requireNonNull(incident);
        Objects.requireNonNull(role);
        EnumSet<Role> current = subscriptions.computeIfAbsent(incident.getIncidentId(),
                id -> EnumSet.noneOf(Role.class));
        if (subscribed && current.add(role)) incident.addObserver(observers.get(role));
        else if (!subscribed && current.remove(role)) incident.removeObserver(observers.get(role));
    }

    public List<String> getNotificationLog() { return new ArrayList<>(notificationLog); }
}
