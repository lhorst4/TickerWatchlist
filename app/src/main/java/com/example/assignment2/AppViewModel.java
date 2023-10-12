package com.example.assignment2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
public class AppViewModel extends ViewModel {
    MutableLiveData<String[]> tickers;
    MutableLiveData<String> suffix;

    public void setTickers(MutableLiveData<String[]> stocks){
        this.tickers = stocks;
    }
    public void setSuffix(MutableLiveData<String> str){ this.suffix = str; }

    public MutableLiveData<String[]> getTickers(){
        if(tickers == null){
            tickers = new MutableLiveData<>();
            String[] tickArray = new String[6];
            tickArray[0] = "BAC";
            tickArray[1] = "BTC";
            tickArray[2] = "AAPL";
            tickers.setValue(tickArray);
        }
        return tickers;
    }

    public MutableLiveData<String> getSuffix(){
        if(suffix == null){
            suffix = new MutableLiveData<>();
            suffix.setValue("");
        }
        return suffix;
    }

    public void addTicker(String tick){
        String[] stocks = tickers.getValue();
        for(int i = 0; i < stocks.length - 1; i++){
            stocks[stocks.length - (i+1)] = stocks[stocks.length - (i+2)];
        }
        stocks[0] = tick;
        tickers.setValue(stocks);
    }

    public void setSuffix(String suff){
        if(suffix == null){
            suffix = new MutableLiveData<>();
        }
        suffix.setValue(suff);
        Log.i("setSuffix", "is changed to " + suff);
    }
}
