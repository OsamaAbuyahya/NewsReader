package os.abuyahya.newsreader.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import os.abuyahya.newsreader.model.Article;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPokemon(ArrayList<Article> articles);

    @Query("SELECT * FROM articles_table")
    LiveData<List<Article>> getArticles();
}
