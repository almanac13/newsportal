package newsportal.strategy;

import newsportal.model.Article;
import newsportal.model.Subscriber;

public class PushStrategy implements NotificationStrategy
{
    @Override
    public void send(Article article, Subscriber subscriber) {
        System.out.println("🔔 [PUSH] Sent " + article.getTitle() + " to " +  subscriber.getName()
                + " (Device:" + subscriber.getDeviceId());

    }
}
