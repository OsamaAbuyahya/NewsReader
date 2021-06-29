package os.abuyahya.newsreader.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import os.abuyahya.newsreader.R;
import os.abuyahya.newsreader.adapter.ArticleAdapter;
import os.abuyahya.newsreader.databinding.FragmentHomeBinding;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;


@AndroidEntryPoint
public class HomeFrag extends Fragment implements View.OnClickListener {


    public HomeFrag() {
        // Required empty public constructor
    }
    SharedPreferences sharedPref;
    private static final int FIRST_PAGE = 0;
    private static final int HITS_PER_PAGE = 10 ;
    private static int CURRENT_PAGE = FIRST_PAGE;
    private ArticleAdapter adapter;
    ArrayList<Article> listOfArticle = new ArrayList<>();
    private FragmentHomeBinding binding;
    private ArticleViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        sharedPref = requireContext().getSharedPreferences("SharedPrf", Context.MODE_PRIVATE);

        initializeRecycler();
        getArticles();


        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){

                    binding.aviLoadingMore.show();
                    getMoreArticle();
                }

            }
        });

        adapter.setOnClickItemListener(new ArticleAdapter.OnItemClicked() {
            @Override
            public void onItemClickListener(String url) {
                NavDirections action = HomeFragDirections.actionHomeFragToArticalFrag(url);
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }
        });
        binding.btnConnect.setOnClickListener(this);
        return binding.getRoot();
    }

    private void getMoreArticle() {
        CURRENT_PAGE +=1;
        viewModel.getArticles(CURRENT_PAGE, HITS_PER_PAGE);

    }

    private void getArticles() {
        if (checkInternetConnection()){
            getArticlesFromAPI();
        } else{
            getArticlesFromDB();
            binding.btnConnect.setVisibility(View.VISIBLE);

        }

    }

    private void getArticlesFromDB() {
        viewModel.getArticlesFormDB();
        viewModel.getmArticleFromDBLiveData().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Article> articles) {
                if (!articles.isEmpty()) {
                    ArrayList<Article> list = (ArrayList<Article>) articles;
                    adapter.setList(list);
                    binding.txtLastSync.setText("last sync on: "+sharedPref.getString("sync_on", ""));
                } else {
                    binding.txtNoLoded.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getArticlesFromAPI() {
        viewModel.getArticles(FIRST_PAGE, HITS_PER_PAGE);
        viewModel.getMutArticleLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                viewModel.insetArticles(articles);
                adapter.setList(articles);
                setSyncSharedPref();
            }
        });
    }

    private void initializeRecycler() {
        binding.recArticle.setHasFixedSize(true);
        binding.recArticle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ArticleAdapter(requireContext());
        binding.recArticle.setAdapter(adapter);
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public void setSyncSharedPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
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
        if (v == binding.btnConnect){
            Toast.makeText(requireContext(), "...", Toast.LENGTH_SHORT).show();
            if (checkInternetConnection()){
                getArticles();
            } else {
                Snackbar.make(binding.getRoot(), "The app stays in \"offline mode\"", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .setAnchorView(binding.btnConnect)
                        .show();
            }
        }
    }
}
