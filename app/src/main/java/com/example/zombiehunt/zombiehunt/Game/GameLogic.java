package com.example.zombiehunt.zombiehunt.Game;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zombiehunt.zombiehunt.R;

import java.util.Random;

//description: this class is for implementing the logics of the game
public class GameLogic {
    private GameBoard gameBoard = GameBoard.getInstance();
    /*
        Minelocation:
        -1 --> slot searched, cannot be searched again
        0  --> slot not searched and with no zombie
        1  --> slot not searched and with zombie
        2  --> slot clicked with zombie revealed, but available for search
    */
    private int Minelocation[][] = new int[gameBoard.getBoardRow()][gameBoard.getBoardCol()];

    private static GameLogic instance;
    private GameLogic(){}

    public static GameLogic getInstance() {
        if (instance == null) {
            instance =  new GameLogic();
        }
        return instance;
    }

    private void InitializingLocation() {
        for(int row = 0; row < gameBoard.getBoardRow(); row++){
            for(int col=0; col < gameBoard.getBoardCol(); col++){
                Minelocation[row][col] = 0;
            }
        }
    }

    public void MinePlacing(){
        InitializingLocation();
        int RowSetter;
        int ColSetter;

        for(int i =0; i < gameBoard.getMineNum(); i++){
            Random RandomMineSetter = new Random();
            RowSetter = RandomMineSetter.nextInt(gameBoard.getBoardRow()-1);
            ColSetter = RandomMineSetter.nextInt(gameBoard.getBoardCol()-1);
            if(Minelocation[RowSetter][ColSetter] != 1){
                Minelocation[RowSetter][ColSetter] = 1;
            }
            else{
                i--;
            }
        }
    }

    public int CheckMine(int ButtonRow, int ButtonCol){
        return Minelocation[ButtonRow][ButtonCol];
    }

    public int ScanCell(int Buttonrow, int Buttoncol){
        int MineScanned = 0;
        gameBoard.IncrementScanUsed();
        //indicate that slot is searched already
        Minelocation[Buttonrow][Buttoncol] = -1;

        //check the col of cell clicked
        for(int col =0; col < gameBoard.getBoardCol();col++){
            if(Minelocation[Buttonrow][col] == 1){
                MineScanned++;
            }
        }
        //check the row of cell clicked
        for(int row=0; row < gameBoard.getBoardRow(); row++) {
            if (Minelocation[row][Buttoncol] == 1) {
                MineScanned++;
            }
        }
        return MineScanned;
    }

    public void UpdateMineStatus(int Buttonrow, int Buttoncol){
        //indicate a hidden mine is found
        Minelocation[Buttonrow][Buttoncol]++;
        gameBoard.IncrementFoundMine();
    }

    public boolean CheckGameStatus(){
        if(gameBoard.getFoundMine() == gameBoard.getMineNum()){
            gameBoard.IncrementGameTrial();
            return true;
        }
        return false;
    }

    public boolean CheckBestScore(){
        int Score = gameBoard.getScanUsed();
        int SavedBestScore = gameBoard.getSpecificGameScores();
        if(Score < SavedBestScore || SavedBestScore == 0){
            return true;
        }
        return false;
    }
}