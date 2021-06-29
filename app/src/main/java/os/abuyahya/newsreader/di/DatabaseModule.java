package os.abuyahya.newsreader.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import os.abuyahya.newsreader.db.ArticleDB;
import os.abuyahya.newsreader.db.ArticleDao;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static ArticleDB provideDB(Application application){

        return Room.databaseBuilder(application, ArticleDB.class, "article_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }


    @Provides
    @Singleton
    public static ArticleDao provideDao(ArticleDB articleDB){

        return articleDB.articleDao();
    }
}
