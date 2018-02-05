package com.example.cubg1_14.todoexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backendless.Backendless;

public class ToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        Backendless.initApp(
            this,
            System.getenv("BACKENDLESS_APP_ID"),
            System.getenv("BACKENDLESS_ANDROID_KEY")
        );
    }
}
