package newsportal;

import newsportal.agency.NewsAgency;
import newsportal.factory.NotificationStrategyFactory;
import newsportal.model.Article;
import newsportal.model.ArticleBuilder;
import newsportal.model.Subscriber;
import newsportal.strategy.EmailStrategy;
import newsportal.strategy.PushStrategy;

public class Main {
    public static void main(String[] args) {
        NewsAgency newsAgency = NewsAgency.getInstance();

        Subscriber mukhtar = new Subscriber(
                "Mukhtar",
                "mukha@gmail.com",
                "87771234455",
                "1",
                NotificationStrategyFactory.createStrategy("email")  // ← Factory pattern!
        );

        newsAgency.registerObserver(mukhtar);

        Subscriber david = new Subscriber(
                "David",
                "david@gmail.com",
                "87001002030",
                "2",
                NotificationStrategyFactory.createStrategy("push")
        );

        newsAgency.registerObserver(david);

        Article article1 = new ArticleBuilder()
                .setTitle("Breaking: Market Hits All-Time High")
                .setContent("Stock markets reached record levels today...")
                .setCategory("Finance")
                .setPriority("HIGH")
                .build();

        newsAgency.publishArticle(article1);

        System.out.println("\n--- Changing David's notification method ---");
        david.setNotificationStrategy(
                NotificationStrategyFactory.createStrategy("email")
        );

        Article article2 = new ArticleBuilder()
                .setTitle("Tech Giant Announces New AI Product")
                .setContent("Leading tech company unveiled breakthrough...")
                .setCategory("Technology")
                // Notice: No setPriority() - will use default "MEDIUM"
                .build();

        newsAgency.publishArticle(article2);
    }
}
