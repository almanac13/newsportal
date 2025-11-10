package newsportal.model;

import java.time.LocalDateTime;

public class ArticleBuilder {
    private String title;
    private String content;
    private Category category;
    private String priority = "MEDIUM";
    private LocalDateTime timestamp;

    public ArticleBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBuilder setCategory(Category category) {  // Changed parameter type
        this.category = category;
        return this;
    }

    public ArticleBuilder setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public ArticleBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Article build() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalStateException("Article must have a title");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalStateException("Article must have content");
        }
        if (category == null) {
            throw new IllegalStateException("Article must have a category");
        }

        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }

        System.out.println("Builder: Constructing article '" + title + "'");
        return new Article(title, content, category, priority);
    }
}