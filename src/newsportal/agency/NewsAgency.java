package newsportal.agency;

import newsportal.model.Article;
import newsportal.observer.Observer;
import newsportal.observer.Subject;

import java.util.ArrayList;

public class NewsAgency implements Subject {
    private ArrayList<Observer> subscribers;
    private Article article;

    private static NewsAgency instance;

    private NewsAgency() {
        this.subscribers = new ArrayList<>();
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
        notifyObservers();
    }
}
