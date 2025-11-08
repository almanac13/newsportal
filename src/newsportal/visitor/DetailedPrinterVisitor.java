package newsportal.visitor;

import newsportal.model.Article;


public class DetailedPrinterVisitor implements ArticleVisitor {

    @Override
    public void visit(Article article) {

        System.out.println("Title: " + article.getTitle());
        System.out.println("Category: " + article.getCategory().getDisplayName());
        System.out.println("Priority: " + article.getPriority());
        System.out.println("Published: " + article.getTimestamp());
        System.out.println("Content: " + article.getContent());
    }
}