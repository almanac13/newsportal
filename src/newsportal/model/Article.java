package newsportal.model;

import newsportal.visitor.ArticleVisitor;

import java.time.LocalDateTime;

public class Article {
    private String title;
    private String content;
    private Category category;  // Changed from String to Category
    private String priority;
    private LocalDateTime timestamp;

    Article(String title, String content, Category category, String priority) {
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

    public Category getCategory() {  // Changed return type
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
                priority, title, category.getDisplayName(), timestamp.toLocalTime());
    }

    // Visitor Pattern
    public void accept(ArticleVisitor visitor) {
        visitor.visit(this);
    }

}