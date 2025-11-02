package newsportal.adapter;

public class LegacyNotificationSystem {
    public void sendOldStyleNotification(String message, String recipient, String channel) {
        System.out.println("[LEGACY-" + channel.toUpperCase() + "] Message: '" + message + "' → Recipient: " + recipient);
    }
    public void logLegacyNotification(String info) {
        System.out.println("Legacy Log: " + info);
    }

}
