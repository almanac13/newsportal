package newsportal.strategy;

import newsportal.model.Article;
import newsportal.model.Subscriber;

public class SmsStrategy implements NotificationStrategy{
    @Override
    public void send(Article article, Subscriber subscriber) {
        System.out.println("Sending article: " + article.getTitle() + " to " + subscriber.getName()
                + "to phone number " + subscriber.getPhone() );

    }
}
