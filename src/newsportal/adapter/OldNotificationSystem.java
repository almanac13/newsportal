package newsportal.adapter;

public class OldNotificationSystem {
    public void sendOldStyleNotification(String message, String recipient, String channel) {
        System.out.println("[" + channel.toUpperCase() + "] Message: '" + message + "' → Recipient: " + recipient);
    }
    public void logOldNotification(String info) {
        System.out.println("Old Log: " + info);
    }

}
