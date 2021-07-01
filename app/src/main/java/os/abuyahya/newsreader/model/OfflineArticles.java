package os.abuyahya.newsreader.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "offline_table")
public class OfflineArticles {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;

    public OfflineArticles(String title) {
        this.title = title;
    }

    public OfflineArticles() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
