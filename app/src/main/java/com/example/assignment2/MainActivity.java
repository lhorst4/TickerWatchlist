package com.example.assignment2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
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

    FragmentManager fg = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        Intent intent = getIntent();
        message = intent.getStringExtra("sms");
        //if(message != null) {
        //    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //}
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
            char[] validChars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
            int startIndex = message.lastIndexOf("Ticker:<<") + 9;
            int endIndex = message.lastIndexOf(">>");
            String ticker = message.substring(startIndex, endIndex);
            // Log.i("TickerDetected", "Ticker = " + ticker);



            boolean validTicker = true;
            char[] charlist = ticker.toCharArray();
            for(int i = 0; i < charlist.length-1; i++){
                char charOfInterest = charlist[i];
                boolean validChar = false;
                for(int j = 0; j < validChars.length; j++){
                    if(validChars[j] == charOfInterest){
                        validChar = true;
                    }
                }
                if(!validChar){
                    validTicker = false;
                    break;
                }

            }
            if(validTicker) {
                //Toast.makeText(this, "Valid ticker!", Toast.LENGTH_LONG).show();
                sharedViewModel.addTicker(ticker.toUpperCase());
                Toast.makeText(this, sharedViewModel.getTop(), Toast.LENGTH_LONG).show();
                fg.findFragmentById(R.id.LVFrag).getView().findViewById(R.id.listOfStocks).invalidate();
                fg.beginTransaction().replace(R.id.LVFrag, new ListViewFragment()).commit();

            }else{
                Toast toast = Toast.makeText(this, "No valid watchlist entry found.", Toast.LENGTH_LONG);
                toast.show();
            }

        }else{
            Toast toast = Toast.makeText(this, "No valid watchlist entry found.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}