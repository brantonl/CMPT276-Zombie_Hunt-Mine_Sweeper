package com.example.zombiehunt.zombiehunt.GameUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zombiehunt.zombiehunt.Game.GameBoard;
import com.example.zombiehunt.zombiehunt.R;

//description: this class is the main page of the game, it serves to allow users to direct into other pages.
public class MainActivity extends AppCompatActivity {

    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOptionBtn();
        setupHelpBtn();
        setupGameBtn();
    }

    private void setupGameBtn() {
        Button OptionBtn = findViewById(R.id.playBtn);
        OptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GameIntent = GamePage.makeIntent(MainActivity.this);
                startActivity(GameIntent);
            }
        });
    }

    private void setupOptionBtn() {
        Button OptionBtn = findViewById(R.id.optionBtn);
        OptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OptionIntent = Option.makeIntent(MainActivity.this);
                startActivity(OptionIntent);
            }
        });
    }

    private void setupHelpBtn(){
        Button HelpBtn = findViewById(R.id.helpBtn);
        HelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HelpIntent = Help.makeIntent(MainActivity.this);
                startActivity(HelpIntent);
            }
        });
    }

}
