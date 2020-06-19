package com.garehn.bloodteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.garehn.bloodteacher.characters.Student;
import com.garehn.bloodteacher.graphics.GameView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    protected GameCore game;
    protected GestureDetector gestureDetector;
    public TextView textGameInfo;

    private String STUDENT_CARD = "INT %s\nFOC %s\n%s/20\n%s";
    private String TEACHER_CARD = "CHAR %s\nPEDA %s";
    private String GAME_CARD = "Round %s\nMoyenne : %s/20";

    private Handler defaultHandler;
    private boolean result = false;

    /*// SENDING RESULT MESSAGES
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                    updateGameInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };*/

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

        textGameInfo = findViewById(R.id.main_game_card);

        updateGameInfo();
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

        int delay = 0;

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
                    if (game.getSelectedStudent().isPlayable()) {

                        game.getGameBoard().getCells(cellX,cellY).setPlayable(false);

                        result = game.getTeacher().attackStudent(game.getSelectedStudent());

                        delay = 4000; //Toast duration

                        //sendToast(game.getTeacher().getLastAttack().getAttackToast(), Toast.LENGTH_SHORT);
                        sendToast(game.getTeacher().getLastAttack().getAttentionToast(), Toast.LENGTH_LONG);
                        if(game.getTeacher().getLastAttack().isfResult()){
                            sendToast(game.getTeacher().getLastAttack().getTeachingToast(), Toast.LENGTH_LONG);
                            sendToast(game.getTeacher().getLastAttack().getResultToast(), Toast.LENGTH_LONG);
                            delay += 7000;
                        }
                        if (result == false) {
                            sendToast("NEXT ROUND", Toast.LENGTH_SHORT);
                            game.nextRound();
                        }
                    }
                    else {
                        Log.i("TEACH_MAIN", game.getSelectedStudent().getName() + " is not playable");
                    }
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateGameInfo();
            }
        }, delay);
        if(result==false) {
            for (int i = 1; i < game.getGameView().getBoardWidth(); i++) {
                for (int j = 1; j < game.getGameView().getBoardHeight(); j++) {
                    game.getGameBoard().getCells(i, j).setPlayable(true);
                }
            }
        }

        //updateGameInfo();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // --- Check grid cell click ---
        if (e.getY() < game.getGameView().gridWidth) {
            int cellX = (int) (e.getX() / game.getGameView().cellWidth);
            int cellY = ((int) (e.getY() / game.getGameView().cellHeight)) - 1;

            if (cellY == 4) {
                if (cellX == 2 || cellX == 3) {
                    sendMessage(game.getTeacher().getName(), String.format(TEACHER_CARD,
                            game.getTeacher().getCharisma(),
                            game.getTeacher().getPedagogy()));
                }
            }

            game.getGameBoard().getCurrentCell().setPosX(cellX);
            game.getGameBoard().getCurrentCell().setPosY(cellY);
            game.getGameView().postInvalidate();
            if (game.checkStudents()) {
                sendMessage(game.getSelectedStudent().getName(),
                        String.format(STUDENT_CARD, game.getSelectedStudent().getIntelligence(),
                                game.getSelectedStudent().getFocus(),
                                game.getSelectedStudent().getMark(),
                                game.getSelectedStudent().getSkills()));
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    // OPEN A MESSAGE BOX
    public void sendMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                })
                .create()
                .show();
    }

    public void sendToast(String message, int time) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, time);
        toast.show();
    }

    public void updateGameInfo(){
        textGameInfo.setText(String.format(GAME_CARD,game.getRound(),game.formatAverageMark(game.calculateAverageMark())));

    }

    public void nextRound(int delay){
    }
}

