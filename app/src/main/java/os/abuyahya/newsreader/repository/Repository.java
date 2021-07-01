package os.abuyahya.newsreader.repository;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import os.abuyahya.newsreader.db.ArticleDao;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.model.ArticleResponse;
import os.abuyahya.newsreader.model.OfflineArticles;
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

    public void insertArticles(List<Article> articles){
        articleDao.insertArticle(articles);
    }

    public void insertOfflineArticles(OfflineArticles articles){
        articleDao.insertOfflineArticles(articles);
    }

    public boolean isOffline(String title) {
        return articleDao.isOfflineArticle(title);
    }

    public void deleteCachingArticles(){
        articleDao.deleteCachingArticles();
    }

    public LiveData<List<Article>> getArticlesFromDB(){
        return articleDao.getArticles();
    }

    public boolean isExisting(String objectID) {
        return articleDao.isExisting(objectID);
    }

    public void deleteOfflineArticle(String title) {
        articleDao.deleteOfflineArticle(title);
    }
}
