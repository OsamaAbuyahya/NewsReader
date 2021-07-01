package os.abuyahya.newsreader.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.model.OfflineArticles;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOfflineArticles(OfflineArticles articles);

    @Query("Select Count(*) From offline_table Where title=:title")
    boolean isOfflineArticle(String title);

    @Query("Delete From articles_table")
    void deleteCachingArticles();

    @Query("SELECT * FROM articles_table")
    LiveData<List<Article>> getArticles();

    @Query("Select Count(*) From articles_table Where objectID=:objectID")
    boolean isExisting(String objectID);

    @Query("Delete From offline_table Where title=:title")
    void deleteOfflineArticle(String title);
}
