package newsportal.visitor;

import newsportal.model.Article;

public interface ArticleVisitor {
    void visit(Article article);
}