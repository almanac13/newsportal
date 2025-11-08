package newsportal.visitor;

import newsportal.model.Article;
import newsportal.model.Category;

public class CounterVisitor implements ArticleVisitor {
    private int highPriorityCount = 0;
    private int mediumPriorityCount = 0;
    private int lowPriorityCount = 0;
    private Category targetCategory = null;
    private int categoryCount = 0;

    public CounterVisitor() {
    }

    public CounterVisitor(Category category) {
        this.targetCategory = category;
    }

    @Override
    public void visit(Article article) {
        switch (article.getPriority()) {
            case "HIGH":
                highPriorityCount++;
                break;
            case "MEDIUM":
                mediumPriorityCount++;
                break;
            case "LOW":
                lowPriorityCount++;
                break;
        }

        if (targetCategory != null && article.getCategory() == targetCategory) {
            categoryCount++;
        }
    }

    public void printResults() {
        System.out.println("ARTICLE COUNT REPORT:");
        System.out.println("HIGH Priority:   " + highPriorityCount);
        System.out.println("MEDIUM Priority: " + mediumPriorityCount);
        System.out.println("LOW Priority:    " + lowPriorityCount);
        System.out.println("Total Articles:  " +
                (highPriorityCount + mediumPriorityCount + lowPriorityCount));

        if (targetCategory != null) {
            System.out.println(targetCategory.getDisplayName() +
                    " articles: " + categoryCount);
        }
    }

    public int getHighPriorityCount() {
        return highPriorityCount;
    }

    public int getTotalCount() {
        return highPriorityCount + mediumPriorityCount + lowPriorityCount;
    }
}