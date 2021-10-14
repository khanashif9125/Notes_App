package com.workforashif.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    ListView listView;
    public static List<String> list;
    ArrayAdapter arrayAdapter;
    public static SharedPreferences sharedPreferences;
    public static int i;
    public static int in=i;
    public static int pos;
    private AdView mAdView;
//    AdView mAdView2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listView);
        list= new ArrayList<>();
        //Adding ads-------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
//        mAdView2=findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView2.loadAd(adRequest);
        mAdView.loadAd(adRequest);
    //---------------------------
        arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        list.clear();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);

                startActivity(intent);
                in=i;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Do you really want to delete this?")
                        .setMessage("Once deleted it cant be restored ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                                int f=sharedPreferences.getInt("num",0);
                                for(int j=pos+1;j<=f;j++){
                                    sharedPreferences.edit().putString(Integer.toString(j-1),sharedPreferences.getString(Integer.toString(j),null)).apply();
                                }
                                sharedPreferences.edit().putInt("num",(f-1)).apply();
                                updatelist();
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add){
            i=sharedPreferences.getInt("num",0);
            i++;
            in=i;
            addPage();
        }

        return true;
    }

    public void addPage(){
        Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
//        intent.putExtra("Name",list.get(i));
        startActivity(intent);


    }

    public void updatelist(){
        list.clear();
        sharedPreferences = getSharedPreferences("com.workforashif.notesapp", MODE_PRIVATE);
        i = sharedPreferences.getInt("num", 0);
        for (int j=0;j<=i;j++) {
            String tex1 = sharedPreferences.getString(Integer.toString(j), "");
            list.add(tex1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       updatelist();
        arrayAdapter.notifyDataSetChanged();
    }
}