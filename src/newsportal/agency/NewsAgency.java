package newsportal.agency;

import newsportal.model.Article;
import newsportal.model.ArticleBuilder;
import newsportal.observer.Observer;
import newsportal.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements Subject {
    private ArrayList<Observer> subscribers;
    private Article article;
    private List<Article> articleHistory;

    private static NewsAgency instance;

    private NewsAgency() {
        this.subscribers = new ArrayList<>();
        this.articleHistory = new ArrayList<>();
        loadPreExistingArticles();
    }

    public static synchronized NewsAgency getInstance() {
        if (instance == null) {
            instance = new NewsAgency();
        }
        return instance;
    }


    @Override
    public void registerObserver(Observer observer) {
        subscribers.add(observer);
        System.out.println("Subscriber registered: " + observer);

    }

    @Override
    public void unregisterObserver(Observer observer) {
        subscribers.remove(observer);
        System.out.println("Subscriber unregistered: " + observer);

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : subscribers) {
            observer.update(article);
        }
    }

    public void publishArticle(Article article) {
        this.article = article;
        articleHistory.add(article);
        notifyObservers();
    }

    private void loadPreExistingArticles() {

        Article article1 = new ArticleBuilder()
                .setTitle("Stock Markets Reach All-Time High")
                .setContent("Major indices closed at record levels today...")
                .setCategory("FINANCE")
                .setPriority("HIGH")
                .build();
        articleHistory.add(article1);

        Article article2 = new ArticleBuilder()
                .setTitle("New Smartphone Released with AI Features")
                .setContent("Tech giant unveils latest device...")
                .setCategory("TECHNOLOGY")
                .setPriority("MEDIUM")
                .build();
        articleHistory.add(article2);

        Article article3 = new ArticleBuilder()
                .setTitle("Local Team Wins Championship")
                .setContent("After thrilling final match...")
                .setCategory("SPORTS")
                .setPriority("MEDIUM")
                .build();
        articleHistory.add(article3);

        Article article4 = new ArticleBuilder()
                .setTitle("New Healthcare Policy Announced")
                .setContent("Government reveals major reforms...")
                .setCategory("POLITICS")
                .setPriority("HIGH")
                .build();
        articleHistory.add(article4);

        Article article5 = new ArticleBuilder()
                .setTitle("Storm Warning Issued for Weekend")
                .setContent("Meteorologists predict heavy rainfall...")
                .setCategory("WEATHER")
                .setPriority("HIGH")
                .build();
        articleHistory.add(article5);
    }

}
