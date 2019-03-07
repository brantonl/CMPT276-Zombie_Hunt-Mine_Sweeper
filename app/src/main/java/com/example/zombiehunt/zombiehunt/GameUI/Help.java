package com.example.zombiehunt.zombiehunt.GameUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zombiehunt.zombiehunt.R;

//description: this class is the UI for the Help page
public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, Help.class);
    }
}
