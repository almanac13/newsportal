package newsportal.model;

import newsportal.observer.Observer;
import newsportal.strategy.NotificationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subscriber implements Observer {
    private String name;
    private String email;
    private String phone;
    private String deviceId;
    private NotificationStrategy notificationStrategy;
    private List<Category> subscribedCategories;

    public Subscriber(String name, String email, String phone, String deviceId,
                      NotificationStrategy notificationStrategy) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.deviceId = deviceId;
        this.notificationStrategy = notificationStrategy;
        this.subscribedCategories = new ArrayList<>();
        // By default, subscribe to all categories
        this.subscribedCategories.addAll(Arrays.asList(Category.values()));
    }

    @Override
    public void update(Article article) {
        // Check if subscriber is interested in this category
        if (subscribedCategories.contains(article.getCategory())) {
            notificationStrategy.send(article, this);
        } else {
            System.out.println(name + " skipped (not subscribed to " +
                    article.getCategory().getDisplayName() + ")");
        }
    }

    public void setNotificationStrategy(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
        System.out.println( name + " changed notification method to " +
                notificationStrategy.getClass().getSimpleName());
    }

    // NEW: Category management methods
    public void subscribeToCategory(Category category) {
        if (!subscribedCategories.contains(category)) {
            subscribedCategories.add(category);
            System.out.println( name + " subscribed to " + category.getDisplayName());
        } else {
            System.out.println( name + " already subscribed to " + category.getDisplayName());
        }
    }

    public void unsubscribeFromCategory(Category category) {
        if (subscribedCategories.remove(category)) {
            System.out.println(  name + " unsubscribed from " + category.getDisplayName());
        } else {
            System.out.println(  name + " was not subscribed to " + category.getDisplayName());
        }
    }

    public void setCategories(List<Category> categories) {
        this.subscribedCategories = new ArrayList<>(categories);
        System.out.println( name + " now follows: " + categoriesToString());
    }

    public List<Category> getSubscribedCategories() {
        return new ArrayList<>(subscribedCategories);
    }

    public String categoriesToString() {
        if (subscribedCategories.isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subscribedCategories.size(); i++) {
            sb.append(subscribedCategories.get(i).getDisplayName());
            if (i < subscribedCategories.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public NotificationStrategy getNotificationStrategy() {
        return notificationStrategy;
    }
}