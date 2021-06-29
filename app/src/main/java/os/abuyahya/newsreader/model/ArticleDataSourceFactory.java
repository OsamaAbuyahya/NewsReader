package os.abuyahya.newsreader.model;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import os.abuyahya.newsreader.repository.Repository;

public class ArticleDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Article>> mArticleLiveData = new MutableLiveData<>();

    public ArticleDataSourceFactory() {
    }

    @Override
    public DataSource create() {
        ArticleDataSource articleDataSource = new ArticleDataSource();
        mArticleLiveData.postValue(articleDataSource);
        return articleDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Article>> getMutArticleLiveData() {
        return mArticleLiveData;
    }


}
