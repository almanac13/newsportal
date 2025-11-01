package newsportal.strategy;

import newsportal.model.Article;
import newsportal.model.Subscriber;

public class EmailStrategy implements NotificationStrategy{
    @Override
    public void send(Article article, Subscriber subscriber) {
        System.out.println("Sending email to " + subscriber.getEmail());
    }
}
