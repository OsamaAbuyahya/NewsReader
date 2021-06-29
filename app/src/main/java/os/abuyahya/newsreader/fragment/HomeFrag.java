package os.abuyahya.newsreader.fragment;

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

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import os.abuyahya.newsreader.adapter.ArticleAdapter;
import os.abuyahya.newsreader.databinding.FragmentHomeBinding;
import os.abuyahya.newsreader.model.Article;
import os.abuyahya.newsreader.viewmodel.ArticleViewModel;


@AndroidEntryPoint
public class HomeFrag extends Fragment {


    public HomeFrag() {
        // Required empty public constructor
    }
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

        initializeRecycler();
        getFirsPageArticles();

        viewModel.getMutArticleLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                viewModel.insetArticles(articles);
                adapter.setList(articles);
            }
        });

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

        return binding.getRoot();
    }

    private void getMoreArticle() {
        CURRENT_PAGE +=1;
        viewModel.getArticles(CURRENT_PAGE, HITS_PER_PAGE);

    }

    private void getFirsPageArticles() {
        viewModel.getArticles(FIRST_PAGE, HITS_PER_PAGE);
    }

    private void initializeRecycler() {
        binding.recArticle.setHasFixedSize(true);
        binding.recArticle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ArticleAdapter(requireContext());
        binding.recArticle.setAdapter(adapter);
    }

}
