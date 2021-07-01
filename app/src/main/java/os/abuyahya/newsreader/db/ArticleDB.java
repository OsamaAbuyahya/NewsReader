package os.abuyahya.newsreader.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.model.OfflineArticles;

@Database(entities = {Article.class, OfflineArticles.class}, version = 2, exportSchema = false)
public abstract class ArticleDB extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
