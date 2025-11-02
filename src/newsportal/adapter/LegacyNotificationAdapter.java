package newsportal.adapter;

import newsportal.model.Article;
import newsportal.model.Subscriber;
import newsportal.strategy.NotificationStrategy;

public class LegacyNotificationAdapter implements NotificationStrategy {
    private LegacyNotificationSystem legacySystem;
    private String channel;

    public LegacyNotificationAdapter(String channel) {
        this.legacySystem = new LegacyNotificationSystem();
        this.channel = channel;
        System.out.println("🔌 Adapter: Connected to legacy " + channel + " system");
    }
    @Override
    public void send(Article article, Subscriber subscriber) {
        // Adapt new interface to old interface
        String message = String.format("[%s] %s",
                article.getPriority(),
                article.getTitle()
        );
        String recipient = subscriber.getName();

        // Call legacy system's method
        legacySystem.sendOldStyleNotification(message, recipient, channel);
        legacySystem.logLegacyNotification(
                "Sent to " + recipient + " via " + channel
        );
    }

    public String getChannel() {
        return channel;
    }

}
