package newsportal.ui;

import newsportal.agency.NewsAgency;
import newsportal.factory.NotificationStrategyFactory;
import newsportal.model.Article;
import newsportal.model.ArticleBuilder;
import newsportal.model.Subscriber;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {
    private NewsAgency agency;
    private Scanner scanner;
    private ArrayList<Subscriber> subscriberList;

    public ConsoleMenu() {
        this.agency = NewsAgency.getInstance();
        this.scanner = new Scanner(System.in);
        this.subscriberList = new ArrayList<>();
    }

    public void start() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📰 WELCOME TO NEWS PORTAL MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Select an option: ");
            switch (choice) {
                case 1:
                    addSubscriber();
                    break;
                case 2:
                    publishArticle();
                    break;
                case 3:
                    changeSubscriberStrategy();
                    break;
                case 4:
                    listSubscribers();
                    break;
                case 5:
                    removeSubscriber();
                    break;
                case 6:
                    System.out.println("\nThank you for using News Portal!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
    private void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU:");
        System.out.println("1.Add New Subscriber");
        System.out.println("2.Publish Article");
        System.out.println("3.Change Subscriber Notification Method");
        System.out.println("4.List All Subscribers");
        System.out.println("5.Remove Subscriber");
        System.out.println("6.Exit");
        System.out.println("-".repeat(60));
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void addSubscriber() {
        System.out.println("\n➕ ADD NEW SUBSCRIBER");
        System.out.println("-".repeat(40));

        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone: ");
        String deviceId = getStringInput("Device ID: ");

        System.out.println("\nChoose notification method:");
        System.out.println("1. Email  2. SMS  3. Push");
        int strategyChoice = getIntInput("Choice: ");

        String strategyType;
        switch (strategyChoice) {
            case 1:
                strategyType = "email";
                break;
            case 2:
                strategyType = "sms";
                break;
            case 3:
                strategyType = "push";
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Email.");
                strategyType = "email";
        }

        try {
            Subscriber subscriber = new Subscriber(
                    name, email, phone, deviceId,
                    NotificationStrategyFactory.createStrategy(strategyType)
            );
            agency.registerObserver(subscriber);
            subscriberList.add(subscriber);
            System.out.println("Subscriber added successfully!");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void publishArticle() {
        System.out.println("\n📝 PUBLISH NEW ARTICLE");
        System.out.println("-".repeat(40));

        String title = getStringInput("Title: ");
        String content = getStringInput("Content: ");
        String category = getStringInput("Category (e.g., Politics, Tech, Sports): ");

        System.out.println("\nPriority:");
        System.out.println("1. HIGH  2. MEDIUM  3. LOW");
        int priorityChoice = getIntInput("Choice: ");

        String priority;
        switch (priorityChoice) {
            case 1:
                priority = "HIGH";
                break;
            case 2:
                priority = "MEDIUM";
                break;
            case 3:
                priority = "LOW";
                break;
            default:
                System.out.println("⚠️  Invalid choice! Defaulting to MEDIUM.");
                priority = "MEDIUM";
        }

        try {
            Article article = new ArticleBuilder()
                    .setTitle(title)
                    .setContent(content)
                    .setCategory(category)
                    .setPriority(priority)
                    .build();

            agency.publishArticle(article);
            System.out.println("Article published successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void changeSubscriberStrategy() {
        if (subscriberList.isEmpty()) {
            System.out.println("⚠ No subscribers available!");
            return;
        }

        System.out.println("\nCHANGE NOTIFICATION METHOD");
        System.out.println("-".repeat(40));
        listSubscribers();

        int index = getIntInput("Select subscriber number: ") - 1;

        if (index < 0 || index >= subscriberList.size()) {
            System.out.println("Invalid subscriber number!");
            return;
        }

        System.out.println("\nNew notification method:");
        System.out.println("1. Email  2. SMS  3. Push");
        int strategyChoice = getIntInput("Choice: ");

        String strategyType;
        switch (strategyChoice) {
            case 1:
                strategyType = "email";
                break;
            case 2:
                strategyType = "sms";
                break;
            case 3:
                strategyType = "push";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        try {
            subscriberList.get(index).setNotificationStrategy(
                    NotificationStrategyFactory.createStrategy(strategyType)
            );
            System.out.println("Notification method updated!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void listSubscribers() {
        if (subscriberList.isEmpty()) {
            System.out.println("No subscribers registered yet.");
            return;
        }

        System.out.println("\n👥 REGISTERED SUBSCRIBERS:");
        System.out.println("-".repeat(40));
        for (int i = 0; i < subscriberList.size(); i++) {
            Subscriber sub = subscriberList.get(i);
            System.out.println(String.format("%d. %s <%s>",
                    i + 1, sub.getName(), sub.getEmail()));
        }
        System.out.println("-".repeat(40));
    }

    private void removeSubscriber() {
        if (subscriberList.isEmpty()) {
            System.out.println("No subscribers to remove!");
            return;
        }

        System.out.println("\n➖ REMOVE SUBSCRIBER");
        System.out.println("-".repeat(40));
        listSubscribers();

        int index = getIntInput("Select subscriber number to remove: ") - 1;

        if (index < 0 || index >= subscriberList.size()) {
            System.out.println("Invalid subscriber number!");
            return;
        }

        Subscriber subscriber = subscriberList.remove(index);
        agency.unregisterObserver(subscriber);
        System.out.println("Subscriber removed successfully!");
    }

}
