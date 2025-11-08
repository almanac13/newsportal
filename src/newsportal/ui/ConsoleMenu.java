package newsportal.ui;

import newsportal.agency.NewsAgency;
import newsportal.factory.NotificationStrategyFactory;
import newsportal.model.Article;
import newsportal.model.ArticleBuilder;
import newsportal.model.Category;
import newsportal.model.Subscriber;
import newsportal.visitor.CounterVisitor;
import newsportal.visitor.DetailedPrinterVisitor;
import newsportal.visitor.SimplePrinterVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        System.out.println("WELCOME TO NEWS PORTAL MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Select an option: ");switch (choice) {
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
                    manageSubscriberCategories();
                    break;
                case 5:
                    listSubscribers();
                    break;
                case 6:
                    viewAllArticles();
                    break;
                case 7:
                    viewArticlesWithStyle();  // NEW!
                    break;
                case 8:
                    countArticles();  // NEW!
                    break;
                case 9:
                    removeSubscriber();
                    break;
                case 0:
                    System.out.println("Thank you for using News Portal!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MAIN MENU:");
        System.out.println("1.Add New Subscriber");
        System.out.println("2.Publish Article");
        System.out.println("3.Change Subscriber Notification Method");
        System.out.println("4.Manage Subscriber Categories");
        System.out.println("5.List All Subscribers");
        System.out.println("6.View All Articles");
        System.out.println("7.View Articles (Different Styles)");  // NEW!
        System.out.println("8.Count Articles");                     // NEW!
        System.out.println("9.Remove Subscriber");
        System.out.println("0.Exit");
        System.out.println("-".repeat(60));
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void addSubscriber() {
        System.out.println("ADD NEW SUBSCRIBER");

        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone: ");
        String deviceId = getStringInput("Device ID: ");

        System.out.println("Choose notification method:");
        System.out.println("1. Email  2. SMS  3. Push");
        System.out.println("4. Fax (Old)  5.Pager (Old)");
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
            case 4:
                strategyType = "fax";
                break;
            case 5:
                strategyType = "pager";
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
        System.out.println(" PUBLISH NEW ARTICLE");
        System.out.println("-".repeat(40));

        String title = getStringInput("Title: ");
        String content = getStringInput("Content: ");

        Category.printAll();
        int categoryChoice = getIntInput("Select category: ");

        if (categoryChoice < 1 || categoryChoice > Category.values().length) {
            System.out.println("Invalid category!");
            return;
        }
        Category category = Category.values()[categoryChoice - 1];

        System.out.println("Priority:");
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
                System.out.println("Invalid choice! Defaulting to MEDIUM.");
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
            System.out.println("No subscribers available!");
            return;
        }

        System.out.println("CHANGE NOTIFICATION METHOD");
        System.out.println("-".repeat(40));
        listSubscribers();

        int index = getIntInput("Select subscriber number: ") - 1;

        if (index < 0 || index >= subscriberList.size()) {
            System.out.println("Invalid subscriber number!");
            return;
        }

        System.out.println("New notification method:");
        System.out.println("1. Email  2. SMS  3. Push");
        System.out.println("4. Fax (Old)  5.Pager (Old)");
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
            case 4:
                strategyType = "fax";
                break;
            case 5:
                strategyType = "pager";
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

        System.out.println("REGISTERED SUBSCRIBERS:");
        for (int i = 0; i < subscriberList.size(); i++) {
            Subscriber sub = subscriberList.get(i);
            System.out.println(
                    (i + 1) + ". " + sub.getName() + " <" + sub.getEmail() + "> - " + sub.getNotificationStrategy().getClass().getSimpleName()
            );
        }
    }

    private void removeSubscriber() {
        if (subscriberList.isEmpty()) {
            System.out.println("No subscribers to remove!");
            return;
        }

        System.out.println("REMOVE SUBSCRIBER");
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

    private void manageSubscriberCategories() {
        if (subscriberList.isEmpty()) {
            System.out.println("No subscribers available!");
            return;
        }

        System.out.println("MANAGE SUBSCRIBER CATEGORIES");
        System.out.println("-".repeat(40));
        listSubscribers();

        int index = getIntInput("Select subscriber number: ") - 1;

        if (index < 0 || index >= subscriberList.size()) {
            System.out.println("Invalid subscriber number!");
            return;
        }

        Subscriber subscriber = subscriberList.get(index);
        System.out.println("Current categories: " + subscriber.categoriesToString());

        System.out.println("1. Add category");
        System.out.println("2. Remove category");
        System.out.println("3. Set specific categories");
        System.out.println("4. Subscribe to all categories");
        int action = getIntInput("Choice: ");

        switch (action) {
            case 1:
                addCategoryToSubscriber(subscriber);
                break;
            case 2:
                removeCategoryFromSubscriber(subscriber);
                break;
            case 3:
                setSubscriberCategories(subscriber);
                break;
            case 4:
                subscriber.setCategories(Arrays.asList(Category.values()));
                System.out.println("Subscribed to all categories!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addCategoryToSubscriber(Subscriber subscriber) {
        Category.printAll();
        int categoryChoice = getIntInput("Select category to add: ");

        if (categoryChoice < 1 || categoryChoice > Category.values().length) {
            System.out.println("Invalid category!");
            return;
        }

        Category category = Category.values()[categoryChoice - 1];
        subscriber.subscribeToCategory(category);
    }

    private void removeCategoryFromSubscriber(Subscriber subscriber) {
        Category.printAll();
        int categoryChoice = getIntInput("Select category to remove: ");

        if (categoryChoice < 1 || categoryChoice > Category.values().length) {
            System.out.println("Invalid category!");
            return;
        }

        Category category = Category.values()[categoryChoice - 1];
        subscriber.unsubscribeFromCategory(category);
    }

    private void setSubscriberCategories(Subscriber subscriber) {
        Category.printAll();
        System.out.println("Enter category numbers separated by commas (e.g., 1,3,5): ");
        String input = getStringInput("");

        try {
            String[] parts = input.split(",");
            List<Category> categories = new ArrayList<>();

            for (String part : parts) {
                int num = Integer.parseInt(part.trim());
                if (num < 1 || num > Category.values().length) {
                    System.out.println("Invalid category number: " + num);
                    return;
                }
                categories.add(Category.values()[num - 1]);
            }

            subscriber.setCategories(categories);
        } catch (NumberFormatException e) {
            System.out.println(" Invalid input format!");
        }
    }

    private void viewAllArticles() {
        List<Article> articles = agency.getAllArticles();

        if (articles.isEmpty()) {
            System.out.println("No articles published yet.");
            return;
        }

        System.out.println("ALL PUBLISHED ARTICLES (" + articles.size() + " total):");
        System.out.println("=".repeat(60));

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            System.out.println((i + 1) + ". " + article.toString());
            System.out.println("   Content: " + article.getContent().substring(0,
                    Math.min(50, article.getContent().length())) + "...");
            System.out.println();
        }
        System.out.println("=".repeat(60));
    }

    private void viewArticlesWithStyle() {
        List<Article> articles = agency.getAllArticles();

        if (articles.isEmpty()) {
            System.out.println("No articles available!");
            return;
        }

        System.out.println("VIEW ARTICLES");
        System.out.println("-".repeat(40));
        System.out.println("1. Simple list");
        System.out.println("2. Detailed view");

        int choice = getIntInput("Choose style: ");

        switch (choice) {
            case 1:
                SimplePrinterVisitor simplePrinter = new SimplePrinterVisitor();
                agency.processWithVisitor(simplePrinter);
                System.out.println("Total: " + simplePrinter.getCount() + " articles");
                break;
            case 2:
                DetailedPrinterVisitor detailedPrinter = new DetailedPrinterVisitor();
                agency.processWithVisitor(detailedPrinter);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void countArticles() {
        List<Article> articles = agency.getAllArticles();

        if (articles.isEmpty()) {
            System.out.println("No articles to count!");
            return;
        }

        System.out.println("COUNT ARTICLES");
        System.out.println("-".repeat(40));
        System.out.println("1. Count by priority");
        System.out.println("2. Count specific category");

        int choice = getIntInput("Choose: ");

        switch (choice) {
            case 1:
                CounterVisitor counter = new CounterVisitor();
                agency.processWithVisitor(counter);
                counter.printResults();
                break;
            case 2:
                Category.printAll();
                int catChoice = getIntInput("Select category: ");
                if (catChoice < 1 || catChoice > Category.values().length) {
                    System.out.println("Invalid category!");
                    return;
                }
                Category category = Category.values()[catChoice - 1];
                CounterVisitor categoryCounter = new CounterVisitor(category);
                agency.processWithVisitor(categoryCounter);
                categoryCounter.printResults();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

}
