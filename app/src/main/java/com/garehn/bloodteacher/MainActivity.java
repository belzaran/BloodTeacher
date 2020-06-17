package com.garehn.bloodteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.gesture.Gesture;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.garehn.bloodteacher.characters.Student;
import com.garehn.bloodteacher.graphics.GameView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    protected GameCore game;
    protected GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGame();
    }

    private void initGame() {
        game = new GameCore();
        game.setGameView((GameView) findViewById(R.id.game_board));
        game.setGameBoard(game.getGameView().getGameBoard());
        game.displayGameBoard();
        gestureDetector = new GestureDetector(this, this);

    }

    /*@Override
    public void onClick(View v) {
        Log.i("TEACH_MAIN","FUCK FUCK FUCK");
    }*/

    @Override
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
        if (e.getY() < game.getGameView().gridWidth) {
            int cellX = (int) (e.getX() / game.getGameView().cellWidth);
            int cellY = (int) (e.getY() / game.getGameView().cellHeight - 1);

            game.getGameBoard().getCurrentCell().setPosX(cellX);
            game.getGameBoard().getCurrentCell().setPosY(cellY);
            game.getGameView().postInvalidate();
            Log.i("TEACH_VIEW", "Touching cell (" + game.getGameBoard().getCurrentCell().getPosX() + "," + game.getGameBoard().getCurrentCell().getPosY() + ")");

            if (game.checkStudents()) {
                {
                    if (game.getTeacher().attackStudent(game.getSelectedStudent()) == false) {
                        game.nextRound();
                    }
                }
            }
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
    }
}