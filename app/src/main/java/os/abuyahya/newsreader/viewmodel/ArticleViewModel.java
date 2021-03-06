package os.abuyahya.newsreader.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.model.ArticleDataSourceFactory;
import os.abuyahya.newsreader.model.ArticleResponse;
import os.abuyahya.newsreader.model.OfflineArticles;
import os.abuyahya.newsreader.repository.Repository;

public class ArticleViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<ArrayList<Article>> mArticleLiveData = new MutableLiveData<>();
    private LiveData<List<Article>> articleFromDBLiveData = null;
    private MutableLiveData<String> mErrorLiveData = new MutableLiveData<>();

    @ViewModelInject
    public ArticleViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Article>> getMutArticleLiveData() {
        return mArticleLiveData;
    }

    public LiveData<List<Article>> getmArticleFromDBLiveData() {
        return articleFromDBLiveData;
    }

    public MutableLiveData<String> getMutErrorLiveData() {
        return mErrorLiveData;
    }

    public void getArticles(int page, int hitsPerPage) {
        repository.getArticles(page, hitsPerPage).subscribeOn(Schedulers.io())
                .map(new Function<ArticleResponse, ArrayList<Article>>() {
                    @Override
                    public ArrayList<Article> apply(ArticleResponse articleResponse) throws Throwable {
                        return articleResponse.getArticles();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> mArticleLiveData.setValue(result),
                        error -> mErrorLiveData.setValue(error.getMessage())
                );
    }

    public void insetArticles(List<Article> articles){
        repository.insertArticles(articles);
    }

    public void insetOfflineArticles(OfflineArticles articles){
        repository.insertOfflineArticles(articles);
    }

    public boolean isOffline(String title) {
        return repository.isOffline(title);
    }

    public void deleteCachingArticles(){
        repository.deleteCachingArticles();
    }

    public void getArticlesFormDB(){
        articleFromDBLiveData = repository.getArticlesFromDB();
    }

    public boolean isExisting(String objectID) {
        return repository.isExisting(objectID);
    }

    public void deleteOfflineArticle(String title) {
        repository.deleteOfflineArticle(title);
    }
}
