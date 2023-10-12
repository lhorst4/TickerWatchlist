package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;

public class WVFragment extends Fragment {

    WebView webView;
    AppViewModel viewModel;
    public WVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_w_v, container, false);
        webView = rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://seekingalpha.com/");


        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        viewModel.getTickers().observe(getViewLifecycleOwner(), new Observer<String[]>(){
            @Override
            public void onChanged(String[] tickers){
                if(viewModel.getSuffix() != null) {
                    String url = "https://seekingalpha.com/symbol/" +
                            viewModel.getSuffix().getValue();
                }else{
                    webView.loadUrl("https://seekingalpha.com/");
                }
            }
        });
    }
}