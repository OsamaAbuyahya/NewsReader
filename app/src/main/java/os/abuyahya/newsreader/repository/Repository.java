package os.abuyahya.newsreader.repository;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import os.abuyahya.newsreader.db.ArticleDao;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.model.ArticleResponse;
import os.abuyahya.newsreader.network.ArticleApiService;

public class Repository {

    private ArticleApiService articleApiService;
    private ArticleDao articleDao;

    @Inject
    public Repository(ArticleApiService articleApiService, ArticleDao articleDao) {
        this.articleApiService = articleApiService;
        this.articleDao = articleDao;
    }

    public Observable<ArticleResponse> getArticles(int page, int hitsPerPage) {
        return articleApiService.getArticles(page, hitsPerPage);
    }

    public void insertArticles(ArrayList<Article> articles){
        articleDao.insertPokemon(articles);
    }

    public Observable<ArrayList<Article>> getArticlesFromDB(){
        return articleDao.getArticles();
    }

}
