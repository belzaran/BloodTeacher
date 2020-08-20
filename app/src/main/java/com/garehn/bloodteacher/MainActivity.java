package com.garehn.bloodteacher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
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
    private String TEACHER_CARD = "CHARISMA : %s\nPEDAGOGY : %s";
    private String GAME_CARD = "Round %s\nAVERAGE MARK : %s\nPHASE : %s\nCLASSE VALUE : %s\nREROLLS : %s";
    private String STUDENT_PROBA = "ATTACK SUCCESS : %s %%";
    private String MSG_START = "Let's start to play Blood Teacher";
    private String MSG_MOVE = "Make the class plan \nChange the placement of the students\nClick on OK when you have finished";
    private String MSG_PET = "Choose your pet\\nClick on OK when you have finished\"";

    private boolean result = false;

    private TextView[] textStudent = new TextView[6];
    private TextView textTeacher;

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
        //sendMessage("PLACEMENT", String.format(MSG_PLACEMENT));
        game.displayGameBoard();
        gestureDetector = new GestureDetector(this, this);
        textGameInfo.setText(String.format(MSG_START));
        buttonValidate.setText("OK");
        //updateGameInfo();
    }

    public void createAssets(){

        textGameInfo = findViewById(R.id.main_game_card);
        textStudent[0] = findViewById(R.id.main_player_0);
        textStudent[1] = findViewById(R.id.main_player_1);
        textStudent[2] = findViewById(R.id.main_player_2);
        textStudent[3] = findViewById(R.id.main_player_3);
        textStudent[4] = findViewById(R.id.main_player_4);
        textStudent[5] = findViewById(R.id.main_player_5);

        textTeacher = findViewById(R.id.main_teacher);

        buttonValidate = findViewById(R.id.main_button_validate);
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.getRound() == 0) {
                    if (game.getPhase() == Phase.START) {
                        game.setPhase(Phase.MOVE);
                        textGameInfo.setText(String.format(MSG_MOVE));
                    } else if (game.getPhase() == Phase.MOVE) {
                        game.setPhase(Phase.PET);
                        textGameInfo.setText(String.format(MSG_PET));
                    } else if (game.getPhase() == Phase.PET) {
                        game.setPhase(Phase.PLAY);
                        textGameInfo.setText(String.format(GAME_CARD, game.getRound(),
                                game.getMarkString((int) game.calculateAverageMark()),
                                game.getPhase(), game.calculateClassValue(), game.getRerolls()));

                    } else {
                        game.setPhase(Phase.PLAY);
                    }
                }
                else{
                    nextRound();
                }

            updateGraphics();
                //removeGraphics();

            }

        });
        // don't write anything here for updating display
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

        //Log.i("TEACH_MAIN", "Button Position : " + bXmin + "," + bXmax+ "," +bYmin+ "," +bYmax);
        //Log.i("TEACH_MAIN", "Click Position : " + x + "," + y);

        // Check grid cell click
        if (e.getY() < game.getGameView().gridWidth) {
            //Convert screen position into grid position
            double X = (e.getX() / (game.getGameView().cellWidth))+0.5;
            double Y = (e.getY() / game.getGameView().cellHeight - 1)+0.5;
            int cellX = (int)Math.floor(X);
            int cellY = (int)Math.floor(Y);

            /*OBSOLETE : old graphics with smaller grid
            int cellX = (int) (e.getX() / (game.getGameView().cellWidth));
            int cellY = (int) (e.getY() / game.getGameView().cellHeight - 1);*/

            game.getGameBoard().getCurrentCell().setPosX(cellX);
            game.getGameBoard().getCurrentCell().setPosY(cellY);
            game.getGameView().postInvalidate();
            Log.i("TEACH_VIEW", "Touching cell (" + game.getGameBoard().getCurrentCell().getPosX() + "," + game.getGameBoard().getCurrentCell().getPosY() + ")");

            // ATTACK
            if (game.getPhase() == Phase.PLAY) { // if Player is allowed to play

                if (game.checkStudents()) { // is the a student on the ceLl ?

                    if (game.getSelectedStudent().isSelected()) { // is a the student already selected ?

                        if (game.getSelectedStudent().isPlayable()) { // is the student playable ?

                            result = game.getTeacher().attackStudent(game.getSelectedStudent(), game.checkClosedStudentsBonus(game.getSelectedStudent()));

                            //delay = 2000; //Toast duration

                                if (game.getTeacher().getLastAttack().isfResult()) {

                                sendToast(game.getTeacher().getLastAttack().getResultToast(), Toast.LENGTH_SHORT, x, y);
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
                    } else {
                        if (game.getSelectedStudent().isPlayable()) {
                            game.selectStudent();
                            //game.getGameBoard().getCells(cellX, cellY).setSelected(true);
                            sendToast(String.format(STUDENT_PROBA, game.getTeacher().getProbability(game.getSelectedStudent(), game.checkClosedStudentsBonus(game.getSelectedStudent()))), Toast.LENGTH_SHORT, x, y);
                        }
                    }
                }

            }

            // MOVEMENT
            else if(game.getPhase() == Phase.MOVE){
                if(game.checkStudents() == false){ // if there is no student on the cell, move the student
                    for (int i = 0; i < game.getStudents().size(); i++) {
                        if (game.getStudents().get(i).isMovable()) {
                            // get the old position of the student
                            int oldX = game.getStudents().get(i).getPosX();
                            int oldY = game.getStudents().get(i).getPosY();
                            // change the position of the student
                            game.getStudents().get(i).setPos(cellX, cellY);
                            game.getStudents().get(i).setMovable(false);

                            // updating gameboard
                            // removing information of old cell
                            game.getGameBoard().getCells(oldX, oldY).setStudent(false);
                            game.getGameBoard().getCells(oldX, oldY).setSelected(false);
                            game.getGameBoard().getCells(oldX, oldY).setMovable(false);

                        }
                }
                }
                else{ // if there is a student of the cell, cancel the movement phase
                    game.getSelectedStudent().setMovable(false);
                }
                game.setPhase(Phase.PLAY);
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

            // set all students not movable
            for(int i =0; i< game.getStudents().size();i++){
                game.getStudents().get(i).setMovable(false);
                game.getGameBoard().getCells(cellX, cellY).setMovable(false);
                game.getGameBoard().getCells(cellX, cellY).setSelected(false);
            }

            game.getGameBoard().getCurrentCell().setPosX(cellX);
            game.getGameBoard().getCurrentCell().setPosY(cellY);
            game.getGameView().postInvalidate();
            if (game.checkStudents()) {
                game.getSelectedStudent().setMovable(true);
                game.getGameBoard().getCells(cellX, cellY).setMovable(true);
                game.setPhase(Phase.MOVE);
                Log.i("TEACH_MAIN",game.getSelectedStudent().getName() + " is movable");
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
        for(int i = 0; i < game.getStudents().size(); i++) {
            textStudent[i].setText(String.format(String.format(STUDENT_CARD, game.getStudents().get(i).getName(),
                    game.getStudents().get(i).getFocus(),
                    game.getStudents().get(i).getIntelligence(),
                    game.getMarkString(game.getStudents().get(i).getMark()),
                    game.getStudents().get(i).getSkillsList())));
            //    private String STUDENT_CARD = "%s\nINT : %s\nFOC : %s\nMARK : %s\n%s";
            textStudent[i].setX(game.getStudents().get(i).getPosX() * game.getGameView().getCellWidth() - game.getGameView().getCellWidth()/2);
            textStudent[i].setY(game.getStudents().get(i).getPosY() * game.getGameView().getCellHeight()- game.getGameView().getCellHeight()/2);

            textTeacher.setText(String.format(String.format(TEACHER_CARD, game.getTeacher().getCharisma(), game.getTeacher().getPedagogy())));
            textTeacher.setX(2*game.getGameView().getCellWidth() - game.getGameView().getCellWidth()/2);
            textTeacher.setY(4*game.getGameView().getCellHeight()- game.getGameView().getCellHeight()/2);

        }

        updateGraphics();
        if(game.getRound() == 5){
            sendMessage("END OF GAME", "AVERAGE MARK : " + game.calculateAverageMark());
        }
    }

    public void updateGraphics(){

        int x;
        int y;

        /*// Unselecting all cells
        for (int i = 0; i < game.getGameView().boardWidth; i++) {
            for (int j = 0; j < game.getGameView().boardHeight; j++) {
                game.getGameBoard().getCells(i, j).setSelected(false);
                game.getGameBoard().getCells(i, j).setPlayable(false);
                game.getGameBoard().getCells(i, j).setMovable(false);
                }
            }*/

        removeGraphics();

        for(int  k = 0; k<game.getStudents().size(); k++){
            x = game.getStudents().get(k).getPosX();
            y = game.getStudents().get(k).getPosY();
            game.getGameBoard().getCells(x,y).setStudent(true);

            if(game.getStudents().get(k).isSelected()){
                game.getGameBoard().getCells(x,y).setSelected(true);
            }
            if(game.getStudents().get(k).isMovable()){
                game.getGameBoard().getCells(x,y).setMovable(true);
            }
            if(game.getStudents().get(k).isPlayable()){
                game.getGameBoard().getCells(x,y).setPlayable(true);
            }
        }
    }

    public void removeGraphics(){

        // Unselecting all cells
        for (int i = 0; i < game.getGameView().boardWidth; i++) {
            for (int j = 0; j < game.getGameView().boardHeight; j++) {
                game.getGameBoard().getCells(i, j).setSelected(false);
                game.getGameBoard().getCells(i, j).setPlayable(false);
                game.getGameBoard().getCells(i, j).setMovable(false);
                game.getGameBoard().getCells(i, j).setStudent(false);
            }
        }
    }

    public void nextRound(){
        game.nextRound();
    }
}

