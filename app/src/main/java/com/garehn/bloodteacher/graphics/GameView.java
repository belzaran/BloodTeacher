package com.garehn.bloodteacher.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class GameView extends View  {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

      public int boardWidth = 6;
    public int boardHeight = 5;
    private GestureDetector gestureDetector;

    protected ImageView[][] board;

    protected GameBoard gameBoard;

    public float cellWidth;
    public float cellHeight;
    public float gridWidth;
    public float gridSeparatorSize;


    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public GameView(Context context)
    {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //gestureDetector = new GestureDetector( getContext(),  this );
        gameBoard = new GameBoard(boardWidth, boardHeight);
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(float cellWidth) {
        this.cellWidth = cellWidth;
    }

    public float getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // We compute some sizes
        gridSeparatorSize = (w / 9f) / 20f;
        gridWidth = w;                                // Size of the grid (it's a square)
        cellWidth = gridWidth / boardWidth;                   // Size of a cell (it's a square too)
        cellHeight = cellWidth;
        //buttonWidth = w / 7f;                           // Size of a button
        //buttonRadius = buttonWidth / 10f;               // Size of the rounded corner for a button
        //buttonMargin = (w - 6*buttonWidth) / 7f;        // Margin between two buttons

        /*
        // We resize for this screen the two images
        eraserBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);
        eraserBitmap = Bitmap.createScaledBitmap(eraserBitmap,
                (int) (buttonWidth*0.8f), (int) (buttonWidth*0.8f), false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pencil);
        pencilBitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (buttonWidth*0.8f), (int) (buttonWidth*0.8), false);
        littlePencilBitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (buttonWidth/3), (int) (buttonWidth/3), false);
        */
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // --- Draw cells ---

        //paint.setTextAlign(Paint.Align.CENTER); // for texts ?

        for (int y = 0; y <boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                int backgroundColor = 0xFFF0F0F0;
                if (gameBoard.getCells(x,y).hasStudent()){

                    // Drawing student information

                    if(gameBoard.getCells(x,y).isPlayable()) {
                        backgroundColor = 0xFFF38D6D;
                    }
                    else{
                        backgroundColor = 0xFFF38D6D;
                    }
                }

                // Draw the background for the cell
                paint.setColor(backgroundColor);
                canvas.drawRect(x * cellWidth, y * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, paint);

                // Draw the border of the cell
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(gridSeparatorSize);
                for (int i = 1; i < boardHeight; i++) {
                    canvas.drawLine(cellHeight, i*cellHeight, (boardWidth-1) * cellWidth,i*cellHeight, paint);
                    //canvas.drawLine(i*cellWidth, cellWidth,i*cellWidth, (boardHeight-1)*cellHeight, paint);
                }
                for (int i = 1; i < boardWidth; i++) {
                    //canvas.drawLine(cellHeight, i*cellHeight, (boardWidth-1) * cellWidth,i*cellHeight, paint);
                    canvas.drawLine(i*cellWidth, cellWidth,i*cellWidth, (boardHeight-1)*cellHeight, paint);
                }
            }

            //Drawing Teacher
            paint.setColor(0xFFF38D6D);
            canvas.drawRect(cellWidth*2, (cellHeight*(boardHeight-1))+gridSeparatorSize/2, cellWidth*4 , cellHeight*boardHeight, paint);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(gridSeparatorSize);
            canvas.drawLine(cellWidth*2,(cellHeight*(boardHeight-1)),cellWidth*2,cellHeight*boardHeight, paint);
            canvas.drawLine(cellWidth*4,(cellHeight*(boardHeight-1)),cellWidth*4 + 2,cellHeight*boardHeight, paint);
            canvas.drawLine(cellWidth*2,cellHeight*boardHeight-gridSeparatorSize/2,cellWidth*4,cellHeight*boardHeight-gridSeparatorSize/2, paint);
        }
            }
}

