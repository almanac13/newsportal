package newsportal.factory;

import newsportal.adapter.OldNotificationAdapter;
import newsportal.strategy.EmailStrategy;
import newsportal.strategy.NotificationStrategy;
import newsportal.strategy.PushStrategy;
import newsportal.strategy.SmsStrategy;

public class NotificationStrategyFactory {
    public static NotificationStrategy createStrategy(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Strategy type cannot be null or empty");
        }
        switch (type.toLowerCase().trim()) {
            case "email":
                System.out.println("Factory: Creating EmailStrategy");
                return new EmailStrategy();
            case "sms":
                System.out.println("Factory: Creating SmsStrategy");
                return new SmsStrategy();
            case "push":
                System.out.println("Factory: Creating PushStrategy");
                return new PushStrategy();
            case "fax":
                System.out.println("Factory: Creating Legacy Fax Adapter");
                return new OldNotificationAdapter("fax");
            case "pager":
                System.out.println("Factory: Creating Legacy Pager Adapter");
                return new OldNotificationAdapter("pager");
            default:
                throw new IllegalArgumentException(
                        "Unknown strategy type: " + type +
                                ". Valid types: email, sms, push, fax, pager"
                );
        }
    }
}
