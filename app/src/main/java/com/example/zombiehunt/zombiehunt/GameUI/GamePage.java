package com.example.zombiehunt.zombiehunt.GameUI;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Vibrator;

import com.example.zombiehunt.zombiehunt.Game.GameBoard;
import com.example.zombiehunt.zombiehunt.Game.GameLogic;
import com.example.zombiehunt.zombiehunt.R;
import com.example.zombiehunt.zombiehunt.WinAlertFragment;

//description: this class is the UI of the game page
public class GamePage extends AppCompatActivity {
    private static final String GAMETRIALPREF = "GameTrialPref";
    private static final String GAMETRIAL = "GameTrial";
    private static final String SCORE = "Score";
    private GameBoard gameBoard;
    private GameLogic gameLogic;
    private Button buttons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        gameBoard = GameBoard.getInstance();
        GetSavedData();
        gameLogic = GameLogic.getInstance();
        populateButtons();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameBoard.ResetScanUsed();
        gameBoard.ResetFoundMine();
    }

    private void GetSavedData() {
        gameBoard.setBoardRow(Option.getBoardRow(this));
        gameBoard.setBoardCol(Option.getBoardCol(this));
        gameBoard.setMineNum(Option.getMineNum(this));
        if(gameBoard.getDataReset()){
            gameBoard.ResetGameTrial();
            SaveGameTrialData(true);
        }
        gameBoard.setGameTrial(GamePage.getGameTrial(this));
        gameBoard.setGameScores(GamePage.getBestScores(this));
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.Button_Table);
        buttons = new Button[gameBoard.getBoardRow()][gameBoard.getBoardCol()];
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final long[] ScanPattern = {0,500,100,500};
        final long[] MineFoundPattern = {0,1000,100,1000,100,1000};
        final MediaPlayer FoundMine = MediaPlayer.create(this,R.raw.mummyzombie_soundbible_com_1966938763);
        final MediaPlayer ScanSound = MediaPlayer.create(this, R.raw.diginacatlitterbox_soundbible_com_816193005);
        gameLogic.MinePlacing();

        for(int row =0; row < gameBoard.getBoardRow(); row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col=0; col < gameBoard.getBoardCol(); col++){
                final int FINAL_Col = col;
                final int FINAL_Row = row;
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                //Make text not crop on small buttons
                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lockButtonSizes();
                        // Found a new zombie
                        if(gameLogic.CheckMine(FINAL_Row,FINAL_Col) == 1){
                            //vibration
                            vibrator.vibrate(VibrationEffect.createWaveform(MineFoundPattern,-1));
                            //sound effect
                            FoundMine.start();
                            //show photo
                            MineClicked(FINAL_Row, FINAL_Col);
                            //update game info
                            gameLogic.UpdateMineStatus(FINAL_Row, FINAL_Col);
                        }
                        // Clicked on an empty slot or clicked on a revealed zombie to search
                        else if(gameLogic.CheckMine(FINAL_Row,FINAL_Col) == 0 || gameLogic.CheckMine(FINAL_Row,FINAL_Col) == 2){
                            //sound effect
                            ScanSound.start();
                            //vibration
                            vibrator.vibrate(VibrationEffect.createWaveform(ScanPattern,-1));
                            button.setText(getString(R.string.ScanResult,gameLogic.ScanCell(FINAL_Row,FINAL_Col)));
                        }
                        updateUI();
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void MineClicked(int row, int col) {
        Button button = buttons[row][col];

        //scale image to button
        int newwidth = button.getWidth();
        int newheight = button.getHeight();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zombie1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newwidth, newheight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        //Change text on button
        button.setText("");
    }

    private void lockButtonSizes() {
        for(int row = 0; row < gameBoard.getBoardRow(); row++){
            for(int col = 0; col < gameBoard.getBoardCol(); col++)
            {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void updateUI() {
        TextView MineDescription = findViewById(R.id.MineDescription);
        TextView ScanDescription = findViewById(R.id.ScanDescription);
        TextView TimesPlayed = findViewById(R.id.TimesPlayed);
        TextView BestScore = findViewById(R.id.BestScore);

        MineDescription.setText(getString(R.string.MineDescription,gameBoard.getFoundMine(), gameBoard.getMineNum()) );
        ScanDescription.setText(getString(R.string.ScanDescription,gameBoard.getScanUsed()));
        TimesPlayed.setText(getString(R.string.TimesPlayed,gameBoard.getGameTrial()));
        BestScore.setText(getString(R.string.BestScore,gameBoard.getBoardRow(),gameBoard.getBoardCol(),gameBoard.getMineNum(),gameBoard.getSpecificGameScores()));

        if(gameLogic.CheckGameStatus()){
            SaveGameTrialData(false);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            WinAlertFragment dialog = new WinAlertFragment();
            dialog.show(manager, "Message Dialog");
        }
    }

    private void SaveGameTrialData(boolean reset) {
        String Score;
        SharedPreferences prefs = this.getSharedPreferences(GAMETRIALPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GAMETRIAL, gameBoard.getGameTrial());
        if(reset){
           Score = getString(R.string.defaultGameScore);
           editor.putString(SCORE, Score);
           gameBoard.setDataReset(false);
        }
        else if(!reset && gameLogic.CheckBestScore()){
            gameBoard.setSpecificGameScore();
            Score = GenerateScoreString();
            editor.putString(SCORE, Score);

            //reset FoundMine and ScanUsed
            gameBoard.ResetScanUsed();
            gameBoard.ResetFoundMine();
        }
        editor.apply();
    }

    private String GenerateScoreString() {
        StringBuilder Score = new StringBuilder();
        int[][] SavedScores = gameBoard.getGameScores();
        for(int i =0; i < 3; i++){
            for(int j =0; j <4;j++) {
                if(i ==2 && j ==3){
                    Score.append(SavedScores[i][j]);
                }
                else{
                    Score.append(SavedScores[i][j]+",");
                }
            }
        }

        return Score.toString();
    }

    private static int getGameTrial(Context context){
        int defaultGameTrial = context.getResources().getInteger(R.integer.defaultGameTrial);
        SharedPreferences prefs = context.getSharedPreferences(GAMETRIALPREF, MODE_PRIVATE);
        int GameTrial = prefs.getInt(GAMETRIAL,defaultGameTrial);
        return GameTrial;
    }

    private static String getBestScores(Context context){
        String defaultScores = context.getResources().getString(R.string.defaultGameScore);
        SharedPreferences prefs = context.getSharedPreferences(GAMETRIALPREF, MODE_PRIVATE);
        String StringScore = prefs.getString(SCORE, defaultScores);

        return StringScore;
    }

    public static Intent makeIntent(Context context) {return new Intent(context, GamePage.class);}
}
