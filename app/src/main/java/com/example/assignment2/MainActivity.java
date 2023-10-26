package com.example.assignment2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AppViewModel sharedViewModel;
    String message;
    String suffixstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        Intent intent = getIntent();
        message = intent.getStringExtra("sms");

        //suffixstr = intent.getStringExtra("suffix");
        //suffix.setValue(suffixstr);
        //sharedViewModel.setSuffix(suffix);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            String[] perms = new String[]{Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, perms, 101);
        }
    }

    public AppViewModel getSharedViewModel(){
        return sharedViewModel;
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        message = intent.getStringExtra("sms");
        Log.i("sms", message);

        if(message.contains("Ticker:<<") && message.contains(">>")){
            String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            int startIndex = message.lastIndexOf("Ticker:<<") + 9;
            int endIndex = message.lastIndexOf(">>");
            String ticker = message.substring(startIndex, endIndex);
            Log.i("TickerDetected", "Ticker = " + ticker);
            boolean validTicker = true;
            for(int i = 0; i < endIndex; i++){
                char charOfInterest = ticker.charAt(i);
                String ch = String.valueOf(charOfInterest);
                if(!validChars.contains(ch)){
                    validTicker = false;
                }
            }
            if(validTicker) {
                sharedViewModel.addTicker(ticker.toUpperCase());
            }else{
                Toast toast = Toast.makeText(this, "No valid watchlist entry found.", Toast.LENGTH_LONG);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(this, "No valid watchlist entry found.", Toast.LENGTH_LONG);
            toast.show();
        }
        // startActivity(getIntent());
    }
}