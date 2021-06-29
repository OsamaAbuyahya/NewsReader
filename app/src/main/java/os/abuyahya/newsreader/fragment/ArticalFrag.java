package os.abuyahya.newsreader.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import os.abuyahya.newsreader.R;
import os.abuyahya.newsreader.databinding.FragmentArticleBinding;

public class ArticalFrag extends Fragment {

    public ArticalFrag() {
        // Required empty public constructor
    }

    ArticalFragArgs args;
    FragmentArticleBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArticleBinding.inflate(inflater, container, false);
        args = ArticalFragArgs.fromBundle(getArguments());

        if (args != null) {

//            // Enable Javascript
//            WebSettings webSettings = binding.webView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//

            // Force links and redirects to open in the WebView instead of in a browser
            binding.webView.setWebViewClient(new WebViewClient());


            WebSettings webView = binding.webView.getSettings();
            webView.setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
            webView.setAppCachePath(requireActivity().getCacheDir().getAbsolutePath());
            webView.setAllowFileAccess( true );
            webView.setAppCacheEnabled( true );
            webView.setJavaScriptEnabled( true );
            webView.setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

            if (!isNetworkAvailable()) { // loading offline
                webView.setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            }
            binding.webView.loadUrl(args.getWebUrl());
        }
        return binding.getRoot();
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
