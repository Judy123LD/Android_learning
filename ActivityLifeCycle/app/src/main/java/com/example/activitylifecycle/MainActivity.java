package com.example.activitylifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            String tempdata=savedInstanceState.getString("data_key");
            Log.d(TAG, tempdata);
        }else{
            Log.d(TAG, "savedInstanceState is null.");
        }

        Button startNormal=findViewById(R.id.start_normal);
        startNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent);
            }
        });

        Button startDialog=findViewById(R.id.start_dialog);
        startDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent2);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempdata="Something you just typed";
        outState.putString("data_key",tempdata);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause:");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy:");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart:");
    }

}
