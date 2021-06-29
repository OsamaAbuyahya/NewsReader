package os.abuyahya.newsreader.network;

import io.reactivex.rxjava3.core.Observable;
import os.abuyahya.newsreader.model.ArticleResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleApiService {

    @GET("search")
    Observable<ArticleResponse> getArticles(@Query("page") int pageNum, @Query("hitsPerPage") int hitsPerPage);
}
