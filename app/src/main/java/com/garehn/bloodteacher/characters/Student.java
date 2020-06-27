package com.garehn.bloodteacher.characters;

import com.garehn.bloodteacher.gameplay.Dice;

import java.util.ArrayList;

public class Student {

    protected String name;
    protected int mark; // 0 to 6 If a student get 0, he's deleted, you can't play on him anymore
    protected int intelligence; // 0 to 6
    protected int focus; // 0 to 6
    protected int posX; // 0 to 4
    protected int posY; // 0 to 3
    protected ArrayList<String> names = new ArrayList<>();
    protected boolean selected = false;
    protected boolean playable = true;

    protected ArrayList<StudentSkills> skills = new ArrayList<>();
    protected ArrayList<String> chosenNames = new ArrayList<>();
    protected int value;
    protected int max = 6;
    protected int min = 0;
    protected Dice dice = new Dice();

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public Student(){
        this.name = "student";
        getRandomStudent();
        this.posX = 0;
        this.posY = 0;
        init();
    }

    public Student(int intelligence, int focus, int mark){
        this.name = "student";
        this.mark = mark;
        this.intelligence = intelligence;
        this.focus = focus;
        this.posX = 0;
        this.posY = 0;
        init();
    }

    public Student(int x, int y){
        this.posX = x;
        this.posY = y;
        getRandomStudent();
        init();
    }


    public void init(){
        generateNames();
        this.intelligence = verifyValue(intelligence, min, max);
        this.focus = verifyValue(focus, min, max);
        this.mark = verifyValue(mark, min, max);
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public int getValue() {
        value = focus*100 + intelligence*100 + mark*100;
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int v){
        this.value += v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPos (int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<StudentSkills> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<StudentSkills> skills) {
        this.skills = skills;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    public int getDistance(){
        int d = 3 - posY;
        return d;
    }

    public void addSkills(StudentSkills s){
        skills.add(s);
    }

    public void generateNames(){
        this.names.add("Boris");
        this.names.add("Tâm-Minh");
        this.names.add("Zoé");
        this.names.add("Lenny");
        this.names.add("Noham");
        this.names.add("Maïlys");
        this.names.add("Aurélia");
        //this.chosenNames.add("Aurélia");
        //this.chosenNames.add("Boris");
        //this.chosenNames.add("Zoé");
        //this.chosenNames.add("Lenny");
    }

    public String setRandomName(ArrayList<String> chosenNames){
        int i = this.names.size();
        String n = "";
        int d = 0;

        do {
            d = (int) (Math.random() * (double) (i - 1));
        }
        while(chosenNames.contains(this.names.get(d).toString()));

        this.name = this.names.get(d);
        return this.name;
        }

    public void addMark(int i){
        mark += i;

        if (mark < 0){ //Students marks can be lower than 0
            mark = 0;
        }
        else if (mark>20){ //Students marks can be higher than 20
            mark = 20;
        }
    }

    public int verifyValue(int charac, int mn, int mx) {
        // set doable values for characteristics
        if (charac > max) {
            charac = max;
        } else if (charac < min) {
            charac = min;
        }
        return charac;
    }

    public int getRandomValue(int mn, int mx){
        return dice.rollDiceWithIntervals(mn,mx);
    }

    public void getRandomStudent(){
        this.intelligence = getRandomValue(2,5);
        this.focus = getRandomValue(2,5);
        this.mark = getRandomValue(2,5);
    }

    public String getSkillsList() {

        String skillList = "";
        int size = this.getSkills().size();

        if (size != 0) {

            for (int i = 0; i < size; i++) {
                skillList += this.getSkills().get(i).toString() + " ";
            }
        }
        return skillList;
    }

    public boolean checkMark(){
        boolean b;
        if(mark == 6){
            b = false;
        }
        else if (mark ==1){
            b = false;
        }
        else{
            b = true;
        }
        return b;
    }

}


