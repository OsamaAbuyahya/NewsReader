package os.abuyahya.newsreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleResponse {

    @SerializedName("hits")
    private ArrayList<Article> articles;

    public ArticleResponse() {
    }

    public ArticleResponse(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
