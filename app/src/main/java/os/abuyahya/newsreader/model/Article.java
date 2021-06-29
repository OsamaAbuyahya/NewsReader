package os.abuyahya.newsreader.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "articles_table")
public class Article {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String created_at, title, url, author, points, story_text, comment_text, story_title, story_url, objectID;
    private int num_comments, story_id, parent_id, created_at_i, relevancy_score;
    @SerializedName("_tags")
    @Ignore
    private ArrayList<String> tags;
    @Ignore
    private HighlightResult _highlightResult;

    public Article() {
    }

    public Article(
            String created_at, String title, String url, String author, String points, String story_text,
            String comment_text, String story_title, String story_url, String objectID, int num_comments,
            int story_id, int parent_id, int created_at_i, int relevancy_score, ArrayList<String> _tags, HighlightResult _highlightResult
    ) {
        this.created_at = created_at;
        this.title = title;
        this.url = url;
        this.author = author;
        this.points = points;
        this.story_text = story_text;
        this.comment_text = comment_text;
        this.story_title = story_title;
        this.story_url = story_url;
        this.objectID = objectID;
        this.num_comments = num_comments;
        this.story_id = story_id;
        this.parent_id = parent_id;
        this.created_at_i = created_at_i;
        this.relevancy_score = relevancy_score;
        this.tags = _tags;
        this._highlightResult = _highlightResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStory_text() {
        return story_text;
    }

    public void setStory_text(String story_text) {
        this.story_text = story_text;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStory_url() {
        return story_url;
    }

    public void setStory_url(String story_url) {
        this.story_url = story_url;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCreated_at_i() {
        return created_at_i;
    }

    public void setCreated_at_i(int created_at_i) {
        this.created_at_i = created_at_i;
    }

    public int getRelevancy_score() {
        return relevancy_score;
    }

    public void setRelevancy_score(int relevancy_score) {
        this.relevancy_score = relevancy_score;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public HighlightResult get_highlightResult() {
        return _highlightResult;
    }

    public void set_highlightResult(HighlightResult _highlightResult) {
        this._highlightResult = _highlightResult;
    }
}
