package newsportal.ui;

import newsportal.agency.NewsAgency;
import newsportal.factory.NotificationStrategyFactory;
import newsportal.model.*;
import newsportal.visitor.*;

import java.util.*;

public class ConsoleMenu {

    private NewsAgency agency = NewsAgency.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private List<Subscriber> subscribers = new ArrayList<>();

    public void start() {
        System.out.println("\n===============================");
        System.out.println("   NEWS PORTAL MANAGEMENT APP  ");
        System.out.println("===============================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getInt("Choose option: ");

            if (choice == 1)
                addSubscriber();
            else if (choice == 2)
                publishArticle();
            else if (choice == 3)
                changeNotificationMethod();
            else if (choice == 4)
                manageCategories();
            else if (choice == 5)
                listSubscribers();
            else if (choice == 6)
                showAllArticles();
            else if (choice == 7)
                showArticlesWithStyle();
            else if (choice == 8)
                countArticles();
            else if (choice == 9)
                removeSubscriber();
            else if (choice == 0) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1. Add Subscriber");
        System.out.println("2. Publish Article");
        System.out.println("3. Change Notification Method");
        System.out.println("4. Manage Categories");
        System.out.println("5. List Subscribers");
        System.out.println("6. View All Articles");
        System.out.println("7. View Articles (Simple/Detailed)");
        System.out.println("8. Count Articles");
        System.out.println("9. Remove Subscriber");
        System.out.println("0. Exit");
    }

    // --- Input helpers ---
    private String getString(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }

    private int getInt(String text) {
        while (true) {
            try {
                System.out.print(text);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
            }
        }
    }

    // --- Features ---
    private void addSubscriber() {
        System.out.println("\n--- ADD SUBSCRIBER ---");
        String name = getString("Name: ");
        String email = getString("Email: ");
        String phone = getString("Phone: ");
        String deviceId = getString("Device ID: ");

        System.out.println("Choose notification: 1.Email  2.SMS  3.Push  4.Fax  5.Pager");
        int choice = getInt("Choice: ");

        String type;
        if (choice == 1)
            type = "email";
        else if (choice == 2)
            type = "sms";
        else if (choice == 3)
            type = "push";
        else if (choice == 4)
            type = "fax";
        else if (choice == 5)
            type = "pager";
        else
            type = "email";

        try {
            Subscriber s = new Subscriber(name, email, phone, deviceId,
                    NotificationStrategyFactory.createStrategy(type));
            agency.registerObserver(s);
            subscribers.add(s);
            System.out.println(" Subscriber added!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void publishArticle() {
        System.out.println("\n--- PUBLISH ARTICLE ---");
        String title = getString("Title: ");
        String content = getString("Content: ");

        Category.printAll();
        int catChoice = getInt("Category: ");
        if (catChoice < 1 || catChoice > Category.values().length) {
            System.out.println("Invalid category!");
            return;
        }
        Category category = Category.values()[catChoice - 1];

        System.out.println("Priority: 1.HIGH  2.MEDIUM  3.LOW");
        int p = getInt("Choice: ");
        String priority;
        if (p == 1) priority = "HIGH";
        else if (p == 3) priority = "LOW";
        else priority = "MEDIUM";

        try {
            Article article = new ArticleBuilder()
                    .setTitle(title)
                    .setContent(content)
                    .setCategory(category)
                    .setPriority(priority)
                    .build();

            agency.publishArticle(article);
            System.out.println("Article published!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void changeNotificationMethod() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers yet!");
            return;
        }

        listSubscribers();
        int index = getInt("Select subscriber #: ") - 1;
        if (index < 0 || index >= subscribers.size()) return;

        System.out.println("New method: 1.Email  2.SMS  3.Push  4.Fax  5.Pager");
        int choice = getInt("Choice: ");
        String type;

        if (choice == 1)
            type = "email";
        else if (choice == 2)
            type = "sms";
        else if (choice == 3)
            type = "push";
        else if (choice == 4)
            type = "fax";
        else if (choice == 5)
            type = "pager";
        else type = "email";

        subscribers.get(index).setNotificationStrategy(
                NotificationStrategyFactory.createStrategy(type)
        );
        System.out.println("Notification updated!");
    }

    private void manageCategories() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers!");
            return;
        }

        listSubscribers();
        int index = getInt("Select subscriber #: ") - 1;
        if (index < 0 || index >= subscribers.size()) return;

        Subscriber sub = subscribers.get(index);
        System.out.println("1.Add category  2.Remove  3.Set  4.All");
        int action = getInt("Action: ");

        if (action == 1)
            addCategory(sub);
        else if (action == 2)
            removeCategory(sub);
        else if (action == 3)
            setCategories(sub);
        else if (action == 4) {
            sub.setCategories(Arrays.asList(Category.values()));
            System.out.println("Subscribed to all!");
        } else System.out.println("Invalid!");
    }

    private void addCategory(Subscriber sub) {
        Category.printAll();
        int choice = getInt("Category to add: ");
        if (choice >= 1 && choice <= Category.values().length)
            sub.subscribeToCategory(Category.values()[choice - 1]);
    }

    private void removeCategory(Subscriber sub) {
        Category.printAll();
        int choice = getInt("Category to remove: ");
        if (choice >= 1 && choice <= Category.values().length)
            sub.unsubscribeFromCategory(Category.values()[choice - 1]);
    }

    private void setCategories(Subscriber sub) {
        System.out.println("Enter categories (e.g. 1,3,5): ");
        String input = getString("");
        List<Category> newCats = new ArrayList<>();

        for (String s : input.split(",")) {
            try {
                int n = Integer.parseInt(s.trim());
                if (n >= 1 && n <= Category.values().length)
                    newCats.add(Category.values()[n - 1]);
            } catch (Exception ignored) {}
        }

        sub.setCategories(newCats);
        System.out.println(" Categories updated!");
    }

    private void listSubscribers() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers.");
            return;
        }
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber s = subscribers.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " - " +
                    s.getNotificationStrategy().getClass().getSimpleName());
        }
    }

    private void removeSubscriber() {
        if (subscribers.isEmpty()) {
            System.out.println("No subscribers!");
            return;
        }

        listSubscribers();
        int index = getInt("Select number to remove: ") - 1;
        if (index < 0 || index >= subscribers.size()) return;

        Subscriber sub = subscribers.remove(index);
        agency.unregisterObserver(sub);
        System.out.println("7Subscriber removed!");
    }

    private void showAllArticles() {
        List<Article> list = agency.getAllArticles();
        if (list.isEmpty()) {
            System.out.println("No articles yet.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Article a = list.get(i);
            System.out.println((i + 1) + ". " + a.getTitle() + " (" + a.getCategory() + ")");
        }
    }

    private void showArticlesWithStyle() {
        List<Article> list = agency.getAllArticles();
        if (list.isEmpty()) {
            System.out.println("No articles.");
            return;
        }

        System.out.println("1.Simple view  2.Detailed view");
        int choice = getInt("Choice: ");

        if (choice == 1) {
            SimplePrinterVisitor simple = new SimplePrinterVisitor();
            agency.processWithVisitor(simple);
            System.out.println("Total: " + simple.getCount());
        } else if (choice == 2) {
            DetailedPrinterVisitor detailed = new DetailedPrinterVisitor();
            agency.processWithVisitor(detailed);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void countArticles() {
        List<Article> list = agency.getAllArticles();
        if (list.isEmpty()) {
            System.out.println("No articles.");
            return;
        }

        System.out.println("1.By Priority  2.By Category");
        int choice = getInt("Choice: ");

        if (choice == 1) {
            CounterVisitor counter = new CounterVisitor();
            agency.processWithVisitor(counter);
            counter.printResults();
        } else if (choice == 2) {
            Category.printAll();
            int cat = getInt("Select category: ");
            if (cat >= 1 && cat <= Category.values().length) {
                CounterVisitor counter = new CounterVisitor(Category.values()[cat - 1]);
                agency.processWithVisitor(counter);
                counter.printResults();
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }
}
