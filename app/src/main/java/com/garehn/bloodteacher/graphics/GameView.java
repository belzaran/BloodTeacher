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

import androidx.annotation.Nullable;

public class GameView extends View  {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public int boardWidth = 4;
    public int boardHeight = 3;
    private GestureDetector gestureDetector;


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

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // We compute some sizes
        gridSeparatorSize = (w / 9f) / 20f;
        gridWidth = w;                                // Size of the grid (it's a square)
        cellWidth = gridWidth / 4f;                   // Size of a cell (it's a square too)
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

        /*paint.setTextAlign( Paint.Align.CENTER ); // for texts ?

        */

        for (int y = 0; y <3; y++) {
            for (int x = 0; x < 4; x++) {
                int backgroundColor = 0xFFF0F0F0;
                if (gameBoard.getCells(x,y).hasStudent()){
                    backgroundColor = Color.RED;
                }

                // Draw the background for the cell

                paint.setColor(backgroundColor);
                canvas.drawRect(x * cellWidth, y * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, paint);

                // Draw the border of the cell
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(gridSeparatorSize);
                for (int i = 0; i <= 3; i++) {
                    canvas.drawLine(0, i*cellHeight, 4 * cellWidth,i*cellHeight, paint);
                    canvas.drawLine(i*cellWidth, 0,i*cellWidth, 3*cellHeight, paint);

                }
            }
        }


        /*
        // --- Buttons bar ---

        float buttonsTop = 9*cellWidth + gridSeparatorSize/2;

        paint.setColor(0xFFC7DAF8);
        canvas.drawRect(0, buttonsTop, gridWidth, getHeight(), paint);

        float buttonLeft = buttonMargin;
        float buttonTop = buttonsTop + buttonMargin;

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(buttonWidth * 0.7f);

        for (int i = 1; i <= 9; i++) {
            paint.setColor( 0xFFFFFFFF );
            // Attention aux new !!! Mais ici, on n'est pas trop gourmand
            // Il existe une autre version de drawRoundRect, mais elle necessite
            // que vous modifiez la version minimale supportee pour Android :-(
            RectF rectF = new RectF(buttonLeft, buttonTop,
                    buttonLeft + buttonWidth, buttonTop + buttonWidth);
            canvas.drawRoundRect(rectF, buttonRadius, buttonRadius, paint);

            paint.setColor( 0xFF000000 );
            canvas.drawText("" + i, rectF.centerX(), rectF.top + rectF.height() * 0.75f, paint);

            if (i != 6) {
                buttonLeft += buttonWidth + buttonMargin;
            } else {
                buttonLeft = buttonMargin;
                buttonTop += buttonWidth + buttonMargin;
            }
        }

        int imageWidth = (int) (buttonWidth * 0.8f);
        int imageMargin = (int) (buttonWidth * 0.1f);

        // --- eraser ---
        paint.setColor(0xFFFFFFFF);
        RectF rectF = new RectF( buttonLeft, buttonTop,
                buttonLeft + buttonWidth, buttonTop + buttonWidth );
        canvas.drawRoundRect( rectF, buttonRadius, buttonRadius, paint );
        canvas.drawBitmap( eraserBitmap,
                buttonLeft + imageMargin, buttonTop + imageMargin, paint );
        buttonLeft += buttonWidth + buttonMargin;

        // --- pencil ---
        paint.setColor(0xFFFFFFFF);
        rectF = new RectF( buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonWidth );
        canvas.drawRoundRect( rectF, buttonRadius, buttonRadius, paint );
        Bitmap bitmap = gameBoard.bigNumber ? pencilBitmap : littlePencilBitmap;
        canvas.drawBitmap( bitmap, buttonLeft + imageMargin, buttonTop + imageMargin, paint );

    }*/
            }


    /*----------------------------------------------------------------------------------------------
    USER INTERACTION
    ----------------------------------------------------------------------------------------------*/
    // Override from View
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    // Override from OnGestureDetector
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        // --- Check grid cell click ---
       /* if (e.getY() < gridWidth) {
            int cellX = (int) (e.getX() / cellWidth);
            int cellY = (int) (e.getY() / cellHeight);

            gameBoard.getCurrentCell().setPosX(cellX);
            gameBoard.getCurrentCell().setPosY(cellY);
            postInvalidate();
            Log.i("TEACH_VIEW", "Touching cell (" + gameBoard.getCurrentCell().getPosX() + "," + gameBoard.getCurrentCell().getPosY() + ")");
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }*/


}

