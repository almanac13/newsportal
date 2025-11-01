package newsportal.strategy;

import newsportal.model.Article;
import newsportal.model.Subscriber;

public interface NotificationStrategy {
    void send(Article article, Subscriber subscriber);
}