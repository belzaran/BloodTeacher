package com.garehn.bloodteacher;

import android.util.Log;
import android.view.View;

import com.garehn.bloodteacher.characters.Student;
import com.garehn.bloodteacher.characters.StudentSkills;
import com.garehn.bloodteacher.characters.Teacher;
import com.garehn.bloodteacher.gameplay.Phase;
import com.garehn.bloodteacher.graphics.GameBoard;
import com.garehn.bloodteacher.graphics.GameView;
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
    protected Phase phase;
    protected int reRolls;
    protected int firstClassValue;
    protected float firstAverageMark;
    protected float progress;

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
        createPhase();
        firstClassValue = calculateClassValue();
        firstAverageMark = calculateAverageMark();
        this.reRolls = calculateReRolls();
        //placeStudents();
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

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        Log.i("TEACH_GAME", "Phase : " + phase);
        this.phase = phase;
    }

    public int getRerolls() {        return reRolls;    }

    public void setRerolls(int rerolls) {        this.reRolls = rerolls;    }

    public float getProgress() {
        return progress;
    }


    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    public void createStudents(){

        students.add(new Student(1,1));
        students.add(new Student(1,3));
        students.add(new Student(2,3));
        students.add(new Student(4,3));
        students.add(new Student(4,1));
        students.add(new Student(2,2));

        for (int i=0;i<6;i++){
            students.get(i).setRandomName(this.chosenNames);
            this.chosenNames.add(students.get(i).getName());
        }
        //students.get(0).addSkills(StudentSkills.HYP);
        //students.get(1).addSkills(StudentSkills.PET);
        //students.get(2).addSkills(StudentSkills.DIS);
    }

    public void createTeacher(){
        teacher = new Teacher("Mendeleiev");
        Log.i("TEACH_CORE","Creating " + teacher.getName());
    }

    public void createPhase(){
        phase = Phase.START;
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
        boolean b = false;
        for(int i = 0; i< 6;i++){
            if(students.get(i).getPosX() == gameBoard.getCurrentCell().getPosX() && students.get(i).getPosY() == gameBoard.getCurrentCell().getPosY()){
                Log.i("TEACH_CORE", students.get(i).getName() + " is on the cell clicked");
                b = true;
            }
            else{
                students.get(i).setSelected(false);
            }
        }
        return b;
    }

    public void selectStudent(){
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
    }

    public Student getSelectedStudent(){
        Student student = new Student();
        for(int i = 0; i< 6;i++){
            if(students.get(i).getPosX() == gameBoard.getCurrentCell().getPosX() && students.get(i).getPosY() == gameBoard.getCurrentCell().getPosY()){
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
            calculateResultGame();
        }
        else{
            Log.i("TEACH_CORE", "Next Round : " + round);
        }

        // set all students playable
        for(int i = 0; i<nbStudent;i++){
            if(students.get(i).checkMark()){
                students.get(i).setPlayable(true);
            }
            else{
                students.get(i).setPlayable(false);
            }
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

    public int calculateClassValue(){
        int v = 0;
        for(int i= 0;i<students.size();i++){
            v += students.get(i).getValue();
        }
        v = (int) v/students.size();
        return v;
    }

    public double compareValue(){ // compare teacher value and class value. If return false = teacher is too strong;
        double v = 0;
        v = calculateClassValue()- teacher.getValue();
        return v;
    }

    public int calculateReRolls(){
        int r = 0;

        r = (int) compareValue()/100;

        return r;
    }

    public void removeReroll(int i){
        reRolls -= i;
    }

    public String getMarkString(int m)
    {
        String mk = "";

        switch(m){
            case 0:
                mk = "lost";
                break;
            case 1:
                mk = "F";
                break;
            case 2:
                mk = "E";
                break;
            case 3:
                mk = "D";
                break;
            case 4:
                mk = "C";
                break;
            case 5:
                mk="B";
                break;
            case 6:
                mk="A";
                break;
        }
        return mk;
    }

    // Calculate bonus for nearby student
    // Negative if malus
    public int checkClosedStudentsBonus(Student student){

        int bonus = 0;
        int x = student.getPosX();
        int y = student.getPosY();
        int dX = 0;
        int dY = 0;

        Log.i("TEACH_GAME", "Bonus " + bonus);

        for (int i = 0; i < getStudents().size(); i++) {
            dX = Math.abs(x - students.get(i).getPosX());
            dY = Math.abs(y - students.get(i).getPosY());

            if (dX == 1 || dY == 1) {
                if (dX < 2 && dY < 2) {
                    if(students.get(i).isPlayable()){
                        bonus -= 1;
                        if (students.get(i).getSkills().contains(StudentSkills.DIS)) {
                            bonus -= 1;
                        }
                        if (students.get(i).getSkills().contains(StudentSkills.FRI)) {
                        bonus += 1;
                        }
                    }
                }
            }
        }

        Log.i("TEACH_GAME", "Bonus " + bonus);
        return bonus;
    }

    public void calculateResultGame(){
                checkMentions();
                checkMarkProgress();

        }

        public void checkMentions() {
            int verygood = 0;
            int good = 0;
            int bad = 0;

            for (int i = 0; i < 6; i++) {
                if (students.get(i).getMark() == 6) {
                    verygood += 1;
                } else if (students.get(i).getMark() == 5) {
                    good += 1;
                } else if (students.get(i).getMark() == 1) {
                    bad += 1;
                }
            }
        }

        public float checkMarkProgress(){
            progress = (calculateAverageMark()/firstAverageMark)*100;
            return progress;
        }
}

