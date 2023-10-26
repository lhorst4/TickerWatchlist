package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;


public class ListViewFragment extends Fragment {

    ListView list;
    String[] stocks;
    private AppViewModel viewModel;
    String suffixstr = "";
    AdapterView.OnItemClickListener selecter = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            suffixstr = list.getItemAtPosition(i).toString();
            // Log.i("CLICKED", suffixstr);
            viewModel.setSuffix(suffixstr);
        }
    };

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AppViewModel.class);
        stocks = viewModel.getTickers().getValue();
        list = rootView.findViewById(R.id.listOfStocks);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stocks);
        list.setAdapter(adapter);
        list.setOnItemClickListener(selecter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(AppViewModel.class);
        /*viewModel.getTickers().observe(getViewLifecycleOwner(), new Observer<String[]>(){
            @Override
            public void onChanged(String[] tickers){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tickers);
                list.setAdapter(adapter);
            }
        });
        viewModel.getTickers().observe(getViewLifecycleOwner() , observer);*/
    }
}