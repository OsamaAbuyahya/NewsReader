package os.abuyahya.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;
import os.abuyahya.newsreader.databinding.ActivityArticleBinding;
import os.abuyahya.newsreader.model.OfflineArticles;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;

@AndroidEntryPoint
public class ArticleActivity extends BaseActivity implements View.OnClickListener {

    ActivityArticleBinding binding;
    ArticleViewModel viewModel;
    Bundle bundle;
    String title, url;
    boolean isOffline = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            title = bundle.getString("Title", "");
            url = bundle.getString("Url", "");

            isOffline = viewModel.isOffline(title);
            if (isOffline){
                binding.icSave.setImageResource(R.drawable.ic_bookmark);
            }

            // Force links and redirects to open in the WebView instead of in a browser
            binding.webView.setWebViewClient(new WebViewClient());

            binding.webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
            binding.webView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
            binding.webView.getSettings().setAllowFileAccess( true );
            binding.webView.getSettings().setAppCacheEnabled( true );
            binding.webView.getSettings().setJavaScriptEnabled( true );
            binding.webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

            if (!checkInternetConnection() && isOffline) { // loading offline
                binding.icSave.setImageResource(R.drawable.ic_bookmark);
                binding.webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            }

            binding.webView.loadUrl(url);

            binding.icSave.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.icSave)){
            if (!isOffline){
                viewModel.insetOfflineArticles(new OfflineArticles(title));
                binding.icSave.setImageResource(R.drawable.ic_bookmark);
            } else {
                viewModel.deleteOfflineArticle(title);
                binding.icSave.setImageResource(R.drawable.ic_round_bookmark_border);
            }
        }
    }
}