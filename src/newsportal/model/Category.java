package newsportal.model;

public enum Category {
    FINANCE("Finance"),
    TECHNOLOGY("Technology"),
    SPORTS("Sports"),
    POLITICS("Politics"),
    ENTERTAINMENT("Entertainment"),
    WEATHER("Weather"),
    HEALTH("Health"),
    SCIENCE("Science");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.displayName.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + text);
    }

    public static void printAll() {
        System.out.println("Available categories:");
        for (int i = 0; i < Category.values().length; i++) {
            System.out.println((i + 1) + ". " + Category.values()[i].getDisplayName());
        }
    }
}