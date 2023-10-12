package com.example.assignment2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private AppViewModel sharedViewModel;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        Intent intent = getIntent();
        message = intent.getStringExtra("sms");



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
    }
}