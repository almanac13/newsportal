package newsportal.visitor;

import newsportal.model.Article;


public class SimplePrinterVisitor implements ArticleVisitor {
    private int count = 0;

    @Override
    public void visit(Article article) {
        count++;
        System.out.println(count + ". " + article.getTitle() +
                " [" + article.getCategory().getDisplayName() + "]");
    }

    public int getCount() {
        return count;
    }
}