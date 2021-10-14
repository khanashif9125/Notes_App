package com.workforashif.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static com.workforashif.notesapp.MainActivity.i;
import static com.workforashif.notesapp.MainActivity.in;
import static com.workforashif.notesapp.MainActivity.list;

public class MainActivity2 extends AppCompatActivity {
    EditText editText;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText = findViewById(R.id.edit_text);
        //Adding ads-------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //---------------------------
        System.out.println(list.size());
        System.out.println(in);

        if(list.size()>in)
            editText.setText(list.get(in));

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String text= editText.getText().toString();
        System.out.println(text);

        SharedPreferences sharedPreferences=getSharedPreferences("com.workforashif.notesapp",MODE_PRIVATE);
        sharedPreferences.edit().putString(Integer.toString(in),text).apply();

        sharedPreferences.edit().putInt("num", i).apply();

    }
}