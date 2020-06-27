package com.garehn.bloodteacher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.garehn.bloodteacher.gameplay.Phase;
import com.garehn.bloodteacher.graphics.GameDialog;
import com.garehn.bloodteacher.graphics.GameView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    protected GameCore game;
    protected GestureDetector gestureDetector;
    public TextView textGameInfo;
    public Button buttonValidate;

    private String STUDENT_CARD = "%s\nFOC : %s\nINT : %s\nMARK : %s\n%s";
    private String TEACHER_CARD = "CHAR : %s\nPEDA : %s";
    private String GAME_CARD = "Round %s\nAVERAGE MARK : %s\nPHASE : %s\nCLASSE VALUE : %s\nREROLLS : %s";
    private String STUDENT_PROBA = "ATTACK SUCCESS : %s %%";

    private boolean result = false;

    private TextView[] textStudent = new TextView[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGame();
    }

    private void initGame() {
        game = new GameCore();
        createAssets();
        game.setGameView((GameView) findViewById(R.id.game_board));
        game.setGameBoard(game.getGameView().getGameBoard());
        updateGameInfo();
        game.displayGameBoard();
        gestureDetector = new GestureDetector(this, this);
    }

    public void createAssets(){

        textGameInfo = findViewById(R.id.main_game_card);
        textStudent[0] = findViewById(R.id.main_player_0);
        textStudent[1] = findViewById(R.id.main_player_1);
        textStudent[2] = findViewById(R.id.main_player_2);
        textStudent[3] = findViewById(R.id.main_player_3);
        textStudent[4] = findViewById(R.id.main_player_4);
        textStudent[5] = findViewById(R.id.main_player_5);

        buttonValidate = findViewById(R.id.main_button_validate);
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setPhase(Phase.PLAY);
                nextRound();
                updateGameInfo();
            }
        });


    }

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

        int x = (int)e.getX();
        int y = (int)e.getY();

        //Coordinates of the button
        int bXmin = (int) buttonValidate.getX();
        int bYmin = (int) buttonValidate.getY();
        int bXmax= (bXmin + buttonValidate.getMeasuredWidth());
        int bYmax= (bYmin + buttonValidate.getMeasuredHeight());

        Log.i("TEACH_MAIN", "Button Position : " + bXmin + "," + bXmax+ "," +bYmin+ "," +bYmax);
        Log.i("TEACH_MAIN", "Click Position : " + x + "," + y);

        /*if(x == buttonValidate.getX() && y == buttonValidate.getY()){
            sendToast("CLICK CLIK CLICK", x,y, Toast.LENGTH_SHORT);
        }*/


        // --- Check grid cell click ---
        if (e.getY() < game.getGameView().gridWidth) {
            int cellX = (int) (e.getX() / game.getGameView().cellWidth);
            int cellY = (int) (e.getY() / game.getGameView().cellHeight - 1);

            // set all cells unselected
            for (int i = 0; i < game.getGameView().boardWidth; i++) {
                for (int j = 0; j < game.getGameView().boardHeight; j++) {
                    game.getGameBoard().getCells(cellX, cellY).setSelected(false);
                    if(game.getSelectedStudent().checkMark() == false){
                        game.getGameBoard().getCells(game.getSelectedStudent().getPosX(), game.getSelectedStudent().getPosY()).setPlayable(false);
                    }
                }
            }

            game.getGameBoard().getCurrentCell().setPosX(cellX);
            game.getGameBoard().getCurrentCell().setPosY(cellY);
            game.getGameView().postInvalidate();
            Log.i("TEACH_VIEW", "Touching cell (" + game.getGameBoard().getCurrentCell().getPosX() + "," + game.getGameBoard().getCurrentCell().getPosY() + ")");


            if (game.getPhase() == Phase.PLAY) { // if Player is allowed to play

                // Player can't play anymore
                //game.setPhase(Phase.WAIT);
                //updateGameInfo();

                if (game.checkStudents()) { // is the a student on the ceLl ?

                    if (game.getSelectedStudent().isSelected()) { // is a the student already selected ?

                        if (game.getSelectedStudent().isPlayable()) { // is the student playable ?

                            game.getGameBoard().getCells(cellX, cellY).setPlayable(false);
                            game.getGameBoard().getCells(cellX, cellY).setSelected(false);

                            result = game.getTeacher().attackStudent(game.getSelectedStudent(), game.checkClosedStudentsBonus(game.getSelectedStudent()));

                            //delay = 2000; //Toast duration

                            //sendToast(game.getTeacher().getLastAttack().getAttentionToast(), Toast.LENGTH_LONG, x, y);
                            if (game.getTeacher().getLastAttack().isfResult()) {
                                //sendToast(game.getTeacher().getLastAttack().getTeachingToast(), Toast.LENGTH_LONG, x, y);
                                sendToast(game.getTeacher().getLastAttack().getResultToast(), Toast.LENGTH_LONG, x, y);
                                //delay += 7000;
                            }
                            if (result == false) {
                                if (game.getRerolls() > 0) {
                                    //sendMessage("ATTACK FAILED", "Do you want to reroll ?");

                                        /*if(askReroll(game.getRerolls())) {
                                            game.removeReroll(1);
                                        }*/
                                    //askQuestion();
                                    sendToast("NEXT ROUND", Toast.LENGTH_SHORT, x, y);
                                    game.nextRound();

                                } else {
                                    sendToast("NEXT ROUND", Toast.LENGTH_SHORT, x, y);
                                    game.nextRound();


                                }

                            }
                        } else {
                            Log.i("TEACH_MAIN", game.getSelectedStudent().getName() + " is not playable");
                        }
                    }
                    else {
                        if(game.getSelectedStudent().isPlayable()) {
                            game.selectStudent();
                            game.getGameBoard().getCells(cellX, cellY).setSelected(true);
                            sendToast(String.format(STUDENT_PROBA, game.getTeacher().getProbability(game.getSelectedStudent(), game.checkClosedStudentsBonus(game.getSelectedStudent()))), Toast.LENGTH_LONG, x, y);
                        }
                    }
                }
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game.setPhase(Phase.PLAY); // Player can play again
                        //updateGameInfo();
                    }
                }, delay);*/
            }

            if (result == false) {
                for (int i = 1; i < game.getGameView().getBoardWidth(); i++) {
                    for (int j = 1; j < game.getGameView().getBoardHeight(); j++) {
                        game.getGameBoard().getCells(i, j).setPlayable(true);
                        game.getGameBoard().getCells(i, j).setSelected(false);
                    }
                }
            }
                    for(int i =0; i< game.getStudents().size();i++){
                        if(game.getStudents().get(i).isSelected()){
                            game.getGameBoard().getCells(game.getStudents().get(i).getPosX(), game.getStudents().get(i).getPosY()).setSelected(true);
                        }
                        else{
                            game.getGameBoard().getCells(game.getStudents().get(i).getPosX(), game.getStudents().get(i).getPosY()).setSelected(false);
                        }
            }
        }
        updateGameInfo();

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
                /*sendMessage(game.getSelectedStudent().getName(),
                        String.format(STUDENT_CARD, game.getSelectedStudent().getIntelligence(),
                                game.getSelectedStudent().getFocus(),
                                game.getMarkString(game.getSelectedStudent().getMark()),
                                game.getSelectedStudent().getSkills()));*/
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

    // OPEN A MESSAGE BOX
    public void sendQuestion(String title, String message) {

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

    public void askQuestion(){
        GameDialog gameDialog = new GameDialog();
        gameDialog.show(getSupportFragmentManager(), "garehn dialog");
    }

    public void sendToast(String message, int time,int x, int y) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, time);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, x, y);
        toast.show();
    }

    public void updateGameInfo(){
        textGameInfo.setText(String.format(GAME_CARD,game.getRound(),game.getMarkString((int)game.calculateAverageMark()),game.getPhase(), game.calculateClassValue(), game.getRerolls()));
        for(int i = 0; i<game.getStudents().size(); i++) {
            //textStudent[i].setText(game.getStudents().get(i).getName());
            textStudent[i].setText(String.format(String.format(STUDENT_CARD, game.getStudents().get(i).getName(),
                    game.getStudents().get(i).getFocus(),
                    game.getStudents().get(i).getIntelligence(),
                    game.getMarkString(game.getStudents().get(i).getMark()),
                    game.getStudents().get(i).getSkillsList())));
            //    private String STUDENT_CARD = "%s\nINT : %s\nFOC : %s\nMARK : %s\n%s";
            textStudent[i].setX(game.getStudents().get(i).getPosX() * game.getGameView().getCellWidth());
            textStudent[i].setY(game.getStudents().get(i).getPosY() * game.getGameView().getCellHeight());
        }
    }

    public void nextRound(){
        game.nextRound();
    }
}

