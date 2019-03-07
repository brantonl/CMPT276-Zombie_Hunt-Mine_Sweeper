package com.example.zombiehunt.zombiehunt.GameUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zombiehunt.zombiehunt.Game.GameBoard;
import com.example.zombiehunt.zombiehunt.R;

//description: this class is for setting up the board size and number of zombies. It also has a button to reset the game data
public class Option extends AppCompatActivity {
    public static final String BOARDROW = "BoardRow";
    public static final String BOARDCOL = "BoardCol";
    public static final String MINENUM = "MineNum";
    public static final String BOARDPREFS = "BoardPrefs";
    public static final String MINEPREFS = "MinePrefs";

    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        gameBoard = GameBoard.getInstance();
        SetupBoard();
        SetupMine();
        SetupResetButton();
    }

    private void SetupBoard() {
        RadioGroup BoardSizeOptions = findViewById(R.id.BoardSizeOptions);
        int[] BoardRowContent = getResources().getIntArray(R.array.BoardRow);
        int[] BoardColContent = getResources().getIntArray(R.array.BoardCol);

        int SavedBoardRow = getBoardRow(this);
        int SavedBoardCol = getBoardCol(this);

        for(int i =0; i < BoardRowContent.length; i++){
            final int BoardRow = BoardRowContent[i];
            final int BoardCol = BoardColContent[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.BoardConfig,BoardRow,BoardCol));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveBoardSize(BoardRow,BoardCol);
                }
            });

            BoardSizeOptions.addView(button);

            //select default button
            if(BoardRow == SavedBoardRow && BoardCol == SavedBoardCol){
                button.setChecked(true);
            }
        }

    }

    private void SetupMine() {
       RadioGroup MineNumOptions = findViewById(R.id.MineNumOptions);
       int[] MineNumContent = getResources().getIntArray(R.array.Mines);

       for(int i =0; i < MineNumContent.length; i++){
           final int MineNum = MineNumContent[i];

           RadioButton button = new RadioButton(this);
           button.setText(getString(R.string.MineNum,MineNum));
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SaveMineNum(MineNum);
               }
           });
           MineNumOptions.addView(button);

           //select default button
           if(MineNum == getMineNum(this)){
               button.setChecked(true);
           }
       }
    }

    private void SetupResetButton() {
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.setDataReset(true);
            }
        });
    }

    private void SaveBoardSize(int BoardRow, int BoardCol) {
        SharedPreferences prefs = this.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARDROW, BoardRow);
        editor.putInt(BOARDCOL, BoardCol);
        editor.apply();
    }

    private void SaveMineNum(int MineNum) {
        SharedPreferences prefs = this.getSharedPreferences(MINEPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MINENUM, MineNum);
        editor.apply();
    }

    public static int getBoardRow(Context context){
        int defaultBoardRow = context.getResources().getInteger(R.integer.defaultBoardRow);
        SharedPreferences prefs = context.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        int BoardRow = prefs.getInt(BOARDROW,defaultBoardRow);
        Log.i("BoardRow",""+BoardRow);
        return BoardRow;
    }

    public static int getBoardCol(Context context){
        int defaultBoardCol = context.getResources().getInteger(R.integer.defaultBoardCol);
        SharedPreferences prefs = context.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        int BoardCol = prefs.getInt(BOARDCOL,defaultBoardCol);
        Log.i("BoardCol",""+BoardCol);
        return BoardCol;
    }

    public static int getMineNum(Context context){
        int defaultMineNum = context.getResources().getInteger(R.integer.defaultMineNum);
        SharedPreferences prefs = context.getSharedPreferences(MINEPREFS, MODE_PRIVATE);
        return prefs.getInt(MINENUM,defaultMineNum);
    }

    public static Intent makeIntent(Context context) {return new Intent(context, Option.class);}
}
