package com.garehn.bloodteacher;

import android.util.Log;
import android.view.View;

import com.garehn.bloodteacher.characters.Student;
import com.garehn.bloodteacher.characters.StudentSkills;
import com.garehn.bloodteacher.characters.Teacher;
import com.garehn.bloodteacher.graphics.GameBoard;
import com.garehn.bloodteacher.graphics.GameView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameCore {


    protected GameView gameView;
    protected GameBoard gameBoard;
    protected ArrayList<Student> students = new ArrayList<>();
    protected Teacher teacher;
    protected int nbStudent = 6;
    protected int round = 0;
    protected int maxRounds = 10;
    protected ArrayList<String> chosenNames = new ArrayList<>();

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public GameCore(int max, int stud){
        Log.i("TEACH_CORE","STARTING GAME ");
        this.maxRounds = max;
        this.nbStudent = stud;
        init();
    }

    public GameCore(){
        Log.i("TEACH_CORE","STARTING GAME ");
        this.maxRounds = 10;
        this.nbStudent = 6;
        init();
    }

    public void init(){
        createStudents();
        createTeacher();
        placeStudents();
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    /*public void createAssets(){
        this.setGameView((GameView) gameView.findViewById(R.id.game_board));
        this.setGameBoard(gameView.getGameBoard());
        displayGameBoard();
        gameView.setOnClickListener(this);
    }*/

    public void createStudents(){

        /*for (int i=0;i<6;i++){
            students.add(new Student());
            Log.i("TEACH_CORE","Creating " + students.get(i).getName());
            students.get(i).setRandomName(this.chosenNames);
            this.chosenNames.add(students.get(i).getName());
        }*/
        students.add(new Student(3,3,10));
        students.add(new Student(4,3,12));
        students.add(new Student(3,2,8));
        students.add(new Student(2,3,5));
        students.add(new Student(3,4,15));
        students.add(new Student(3,5,10));
        for (int i=0;i<6;i++){
            students.get(i).setRandomName(this.chosenNames);
            this.chosenNames.add(students.get(i).getName());
        }
        students.get(0).addSkills(StudentSkills.HYP);
        students.get(1).addSkills(StudentSkills.PET);
        students.get(2).addSkills(StudentSkills.DIS);
    }

    public void createTeacher(){
        teacher = new Teacher("Mendeleiev");
        Log.i("TEACH_CORE","Creating " + teacher.getName());
    }

    public void placeStudents() {
        students.get(0).setPos(1, 1);
        students.get(1).setPos(1, 2);
        students.get(2).setPos(2, 2);
        students.get(3).setPos(3, 3);
        students.get(4).setPos(4, 1);
        students.get(5).setPos(3, 1);
    }

    public void displayGameBoard(){
        for(int i = 0;i < 6;i++){
            gameBoard.getCells(students.get(i).getPosX(),students.get(i).getPosY()).setStudent(true);
        }
    }

    public void selectStudent(int s){

        for(int i = 0; i< 6; i++){
            if(i == s){
                students.get(i).setSelected(true);
            }
            else{
                students.get(i).setSelected(false);
            }
        }
    }

    public boolean checkStudents(){
        boolean b =false;
        for(int i = 0; i< 6;i++){
            if(students.get(i).getPosX() == gameBoard.getCurrentCell().getPosX() && students.get(i).getPosY() == gameBoard.getCurrentCell().getPosY()){
                students.get(i).setSelected(true);
                Log.i("TEACH_CORE", students.get(i).getName() + " is selected");
                b= true;
            }
            else{
                students.get(i).setSelected(false);
            }
        }
        return b;
    }

    public Student getSelectedStudent(){
        Student student = new Student();
        for(int i = 0; i< 6;i++){
            if(students.get(i).isSelected()){
                student = students.get(i);
            }
            else{
            }
        }
        return student;
    }

    public int nextRound(){
        this.round += 1;

        // check the end of game
        if(checkEndOfGame()){
            Log.i("TEACH_CORE", "End of game");
        }
        else{
            Log.i("TEACH_CORE", "Next Round : " + round);
        }

        // set all students playable
        for(int i = 0; i<nbStudent;i++){
            students.get(i).setPlayable(true);
        }

        return round;
    }

    public boolean checkEndOfGame(){
        boolean b = false;
        if (round >= maxRounds){
            b = true;
        }
        return b;
    }

    public float calculateAverageMark(){
        int total = 0;

        for(int i = 0 ; i < students.size();i++){
            total += students.get(i).getMark();
        }
        float average = total/students.size();

        DecimalFormat df = new DecimalFormat("#.#");
        df.format(average); //Le nombre sera arrondi à 0,91239.

        return average;
    }

    public String formatAverageMark(float f){
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(f);
    }

}

