package os.abuyahya.newsreader.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import os.abuyahya.newsreader.di.RetrofitModule;
import os.abuyahya.newsreader.repository.Repository;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;

public class ArticleDataSource extends PageKeyedDataSource<Integer, Article> {

    public static final int PAGE_SIZE = 10;
    public static final int FIRST_PAGE = 0;

    public ArticleDataSource() {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        RetrofitModule.provideArticleApiService()
                .getArticles(FIRST_PAGE, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArticleResponse, ArrayList<Article>>() {
                    @Override
                    public ArrayList<Article> apply(ArticleResponse articleResponse) throws Throwable {
                        return articleResponse.getArticles();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        results -> callback.onResult(results, null, FIRST_PAGE+1),
                        error -> Log.d("ArticleDataSource", "Error Msg: " + error.getMessage())
                );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {

        RetrofitModule.provideArticleApiService()
                .getArticles(params.key, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArticleResponse, ArrayList<Article>>() {
                    @Override
                    public ArrayList<Article> apply(ArticleResponse articleResponse) throws Throwable {
                        return articleResponse.getArticles();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        results -> {
                            int key = (params.key>0) ? params.key-1 : null;
                            callback.onResult(results, key);
                        },
                        error -> Log.d("ArticleDataSource", "Error Msg: " + error.getMessage())
                );

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {

        RetrofitModule.provideArticleApiService()
                .getArticles(params.key, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArticleResponse, ArrayList<Article>>() {
                    @Override
                    public ArrayList<Article> apply(ArticleResponse articleResponse) throws Throwable {
                        return articleResponse.getArticles();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        results -> {
                            int key = (params.key<99) ? params.key+1 : null;
                            callback.onResult(results, key);
                        },
                        error -> Log.d("ArticleDataSource", "Error Msg: " + error.getMessage())
                );
    }
}
