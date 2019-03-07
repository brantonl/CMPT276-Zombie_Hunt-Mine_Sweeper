package com.example.zombiehunt.zombiehunt.Game;

import java.util.StringTokenizer;

//description: this class is the singleton that stores the data required for the game
public class GameBoard {

    private final String GAMETRIAL = "GameTrial";

    private static GameBoard instance;
    private int BoardCol;
    private int BoardRow;
    private int MineNum;
    private int ScanUsed;
    private int FoundMine;
    private int GameTrial;
    private int[][] GameScores = new int[3][4];
    private boolean DataReset;

    private GameBoard(){
        //Private to prevent anyone else from instantiating
        ScanUsed = 0;
        FoundMine = 0;
        DataReset = false;
    }

    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    // getters
    public int getBoardRow(){
        return BoardRow;
    }

    public int getBoardCol(){
        return BoardCol;
    }

    public int getMineNum() {
        return MineNum;
    }

    public int getScanUsed() {
        return ScanUsed;
    }

    public int getFoundMine() {
        return FoundMine;
    }

    public int getGameTrial() {
        return GameTrial;
    }

    public int getSpecificGameScores(){
        int RowIndex = 0;
        int ColIndex = 0;
        switch(BoardRow){
            case 4:
                break;

            case 5:
                RowIndex = 1;
                break;

            default:
                RowIndex = 2;
        }
        switch(MineNum){
            case 6:
                break;

            case 10:
                ColIndex = 1;
                break;

            case 15:
                ColIndex = 2;
                break;

            default:
                ColIndex = 3;
        }
        return GameScores[RowIndex][ColIndex];
    }

    public int[][] getGameScores(){
        return GameScores;
    }

    public boolean getDataReset(){
        return DataReset;
    }

    //setters
    public void setBoardRow(int BoardRow){
        this.BoardRow = BoardRow;
    }

    public void setBoardCol(int BoardCol){
        this.BoardCol = BoardCol;
    }

    public void setMineNum(int MineNum){
        this.MineNum = MineNum;
    }

    public void setGameTrial(int GameTrial){
        this.GameTrial = GameTrial;
    }

    public void setGameScores(String Scores){
        StringTokenizer tokenizer = new StringTokenizer(Scores, ",");

        for(int i =0; i < 3; i++){
            for(int j =0; j <4;j++) {
                GameScores[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    public void setDataReset(boolean status){
        this.DataReset = status;
    }

    public void setSpecificGameScore(){
        int RowIndex = 0;
        int ColIndex = 0;
        switch(BoardRow){
            case 4:
                break;

            case 5:
                RowIndex = 1;
                break;

            default:
                RowIndex = 2;
        }
        switch(MineNum){
            case 6:
                break;

            case 10:
                ColIndex = 1;
                break;

            case 15:
                ColIndex = 2;
                break;

            default:
                ColIndex = 3;
        }
        this.GameScores[RowIndex][ColIndex] = ScanUsed;
    }

    //reset variables
    public void ResetGameTrial(){
        this.GameTrial = 0;
    }

    public void ResetScanUsed(){
        this.ScanUsed = 0;
    }

    public void ResetFoundMine(){
        this.FoundMine = 0;
    }

    //increment variables
    public void IncrementGameTrial(){
        GameTrial++;
    }

    public void IncrementFoundMine(){
        FoundMine++;
    }

    public void IncrementScanUsed(){
        ScanUsed++;
    }
}
