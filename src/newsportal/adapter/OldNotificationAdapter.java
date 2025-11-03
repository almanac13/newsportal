package newsportal.adapter;

import newsportal.model.Article;
import newsportal.model.Subscriber;
import newsportal.strategy.NotificationStrategy;

public class OldNotificationAdapter implements NotificationStrategy {
    private OldNotificationSystem oldSystem;
    private String channel;

    public OldNotificationAdapter(String channel) {
        this.oldSystem = new OldNotificationSystem();
        this.channel = channel;
        System.out.println(" Adapter: Connected to legacy " + channel + " system");
    }
    @Override
    public void send(Article article, Subscriber subscriber) {
        // Adapt new interface to old interface
        String message = "[" + article.getPriority() + "] " + article.getTitle();
        String recipient = subscriber.getName();


        // Call legacy system's method
        oldSystem.sendOldStyleNotification(message, recipient, channel);
        oldSystem.logOldNotification(
                "Sent to " + recipient + " via " + channel
        );
    }

    public String getChannel() {
        return channel;
    }

}
