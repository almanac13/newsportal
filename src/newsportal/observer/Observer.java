package newsportal.observer;

import newsportal.model.Article;

public interface Observer {
    void update(Article article);
}
