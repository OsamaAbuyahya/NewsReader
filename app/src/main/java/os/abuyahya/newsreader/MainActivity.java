package os.abuyahya.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import os.abuyahya.newsreader.adapter.ArticleAdapter;
import os.abuyahya.newsreader.databinding.ActivityMainBinding;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int FIRST_PAGE = 0;
    private static final int HITS_PER_PAGE = 10;
    private static int CURRENT_PAGE = FIRST_PAGE;
    private ArticleAdapter adapter;
    private ArticleViewModel viewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);

        initializeRecycler();
        getArticles();

        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    if (checkInternetConnection()) {
                        binding.aviLoadingMore.show();
                        getMoreArticle();
                    }
                }
            }
        });

        adapter.setOnClickItemListener(new ArticleAdapter.OnItemClicked() {
            @Override
            public void onItemClickListener(String url, String title) {
                if (url != null) {
                    Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                    intent.putExtra("Url", url);
                    intent.putExtra("Title", title);
                    startActivity(intent);
                }
            }
        });

        binding.btnConnect.setOnClickListener(this);
    }

    private void initializeRecycler() {
        binding.recArticle.setHasFixedSize(true);
        binding.recArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ArticleAdapter(this, viewModel);
        binding.recArticle.setAdapter(adapter);
    }

    private void getArticles() {
        if (checkInternetConnection()) {
            getArticlesFromAPI();
        } else {
            getArticlesFromDB();
            binding.btnConnect.setVisibility(View.VISIBLE);
        }
    }

    private void getMoreArticle() {
        CURRENT_PAGE += 1;
        viewModel.getArticles(CURRENT_PAGE, HITS_PER_PAGE);
    }

    private void getArticlesFromDB() {
        viewModel.getArticlesFormDB();
        viewModel.getmArticleFromDBLiveData().observe(this, new Observer<List<Article>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Article> articles) {
                if (!articles.isEmpty()) {
                    ArrayList<Article> list = (ArrayList<Article>) articles;
                    adapter.setList(list);
                    binding.txtLastSync.setText("last sync on: " + sharedPref.getString("sync_on", ""));
                } else {
                    binding.txtNoLoded.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getArticlesFromAPI() {
        viewModel.getArticles(FIRST_PAGE, HITS_PER_PAGE);
        viewModel.getMutArticleLiveData().observe(this, new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                adapter.setList(articles);
                setSyncSharedPref();
                if (!viewModel.isExisting(articles.get(0).getObjectID())) {
                    viewModel.insetArticles(articles);
                }
            }
        });
    }

    private void setSyncSharedPref() {
        editor = sharedPref.edit();
        editor.putString("sync_on", getDate());
        editor.apply();
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnConnect) {
            if (checkInternetConnection()) {
                finish();
                startActivity(getIntent());
                viewModel.deleteCachingArticles();
            } else {
                showSnackbar(binding.getRoot(), binding.btnConnect, "The app is stays in \" offline mode \"");
            }
        }
    }
}
