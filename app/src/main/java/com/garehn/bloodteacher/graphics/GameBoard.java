package com.garehn.bloodteacher.graphics;

import android.util.Log;

public class GameBoard {

    protected GameCell currentCell;
    protected GameCell [][] cells;

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public GameBoard(int width, int height){
        Log.i("TEACH_BOARD", "Creating gameboard (" + width + "," + height +")");
        cells = new GameCell[width][height];
        currentCell = new GameCell();
        for(int i = 0; i < width; i++){
            for (int j=0; j<height;j++){
                cells[i][j] = new GameCell();
            }
        }
    }

    public GameBoard() {

        this.cells = cells;
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public GameCell getCells(int x, int y) {
        return cells[x][y];
    }

    public void setCells(GameCell cell, int x, int y) {
        this.cells[x][y] = cell;
    }

    public GameCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(GameCell currentCell) {
        this.currentCell = currentCell;
    }

}
