package newsportal.agency;

import newsportal.model.Article;
import newsportal.model.ArticleBuilder;
import newsportal.model.Category;
import newsportal.observer.Observer;
import newsportal.observer.Subject;
import newsportal.visitor.ArticleVisitor;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements Subject {
    private static NewsAgency instance;
    private List<Observer> subscribers;
    private Article latestArticle;
    private List<Article> articleHistory;

    private NewsAgency() {
        this.subscribers = new ArrayList<>();
        this.articleHistory = new ArrayList<>();
        loadPreExistingArticles();
    }

    public static synchronized NewsAgency getInstance() {
        if (instance == null) {
            instance = new NewsAgency();
            System.out.println("NewsAgency instance created (Singleton)");
        }
        return instance;
    }

    private void loadPreExistingArticles() {
        System.out.println("Loading existing articles...");

        Article article1 = new ArticleBuilder()
                .setTitle("Stock Markets Reach All-Time High")
                .setContent("Major indices closed at record levels today...")
                .setCategory(Category.FINANCE)
                .setPriority("HIGH")
                .build();
        articleHistory.add(article1);

        Article article2 = new ArticleBuilder()
                .setTitle("New Smartphone Released with AI Features")
                .setContent("Tech giant unveils latest device...")
                .setCategory(Category.TECHNOLOGY)
                .setPriority("MEDIUM")
                .build();
        articleHistory.add(article2);

        Article article3 = new ArticleBuilder()
                .setTitle("Local Team Wins Championship")
                .setContent("After thrilling final match...")
                .setCategory(Category.SPORTS)
                .setPriority("MEDIUM")
                .build();
        articleHistory.add(article3);

        Article article4 = new ArticleBuilder()
                .setTitle("New Healthcare Policy Announced")
                .setContent("Government reveals major reforms...")
                .setCategory(Category.POLITICS)
                .setPriority("HIGH")
                .build();
        articleHistory.add(article4);

        Article article5 = new ArticleBuilder()
                .setTitle("Storm Warning Issued for Weekend")
                .setContent("Meteorologists predict heavy rainfall...")
                .setCategory(Category.WEATHER)
                .setPriority("HIGH")
                .build();
        articleHistory.add(article5);

        System.out.println("Loaded " + articleHistory.size() + " existing articles");
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!subscribers.contains(observer)) {
            subscribers.add(observer);
            System.out.println("Subscriber added. Total: " + subscribers.size());
        } else {
            System.out.println("Subscriber already exists!");
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        if (subscribers.remove(observer)) {
            System.out.println("Subscriber removed. Total: " + subscribers.size());
        } else {
            System.out.println(" Subscriber not found!");
        }
    }

    @Override
    public void notifyObservers() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers to notify!");
            return;
        }
        System.out.println("Broadcasting to " + subscribers.size() + " subscribers:");

        for (Observer observer : subscribers) {
            observer.update(latestArticle);
        }
    }

    public void publishArticle(Article article) {
        System.out.println("Publishing: " + article);
        this.latestArticle = article;
        articleHistory.add(article);  // Add to history
        notifyObservers();
    }


    public List<Article> getAllArticles() {
        return new ArrayList<>(articleHistory);
    }


    public List<Article> getArticlesByCategory(Category category) {
        List<Article> filtered = new ArrayList<>();
        for (Article article : articleHistory) {
            if (article.getCategory() == category) {
                filtered.add(article);
            }
        }
        return filtered;
    }

    public List<Observer> getSubscribers() {
        return new ArrayList<>(subscribers);
    }

    // Visitor Pattern support
    public void processWithVisitor(ArticleVisitor visitor) {
        System.out.println("Processing " + articleHistory.size() + " articles.");
        for (Article article : articleHistory) {
            article.accept(visitor);
        }
        System.out.println("Processing complete!");
    }

}