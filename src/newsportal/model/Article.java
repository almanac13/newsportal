package newsportal.model;

import java.time.LocalDateTime;

public class Article {
    private String title;
    private String content;
    private String category;
    private String priority;
    private LocalDateTime timestamp;

    Article(String title, String content, String category, String priority) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority;
        this.timestamp = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s",
                priority, title, category, timestamp.toLocalTime());
    }
}
