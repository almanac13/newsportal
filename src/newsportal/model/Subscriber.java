package newsportal.model;


import newsportal.observer.Observer;
import newsportal.strategy.NotificationStrategy;

public class Subscriber implements Observer {
    private String name;
    private String email;
    private String phone;
    private String deviceId;
    private NotificationStrategy notificationStrategy;

    public Subscriber(String name, String email, String phone, String deviceId, NotificationStrategy notificationStrategy) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.deviceId = deviceId;
        this.notificationStrategy = notificationStrategy;
    }

    public void update(Article article) {
        notificationStrategy.send(article, this);
    }

    public void setNotificationStrategy(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
        System.out.println("✅ " + name + " changed notification method to " +
                notificationStrategy.getClass().getSimpleName());
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
