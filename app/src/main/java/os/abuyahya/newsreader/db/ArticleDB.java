package os.abuyahya.newsreader.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import os.abuyahya.newsreader.model.Article;

@Database(entities = Article.class, version = 1, exportSchema = false)
public abstract class ArticleDB extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
