package com.garehn.bloodteacher.characters;

import java.util.ArrayList;

public class Student {

    protected String name;
    protected int mark; // 0 to 20
    protected int intelligence; // 0 to 6
    protected int focus; // 0 to 6
    protected int insolence; // 0 to 6
    protected int posX; // 0 to 4
    protected int posY; // 0 to 3

    protected boolean selected = false;



    protected ArrayList<StudentSkills> skills = new ArrayList<>();

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public Student(){

    }

    public Student(String n){
        this.name = n;
        this.mark = 10;
        this.intelligence = 3;
        this.insolence = 3;
        this.focus = 3;
        this.posX = 0;
        this.posY = 0;

    }

    public Student(String name, int mark, int intelligence, int focus, int insolence, int posX, int posY) {
        this.name = name;
        this.mark = mark;
        this.intelligence = intelligence;
        this.focus = focus;
        this.insolence = insolence;
        this.posX = posX;
        this.posY = posY;
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

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

    public int getInsolence() {
        return insolence;
    }

    public void setInsolence(int insolence) {
        this.insolence = insolence;
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

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    public int getDistance(){
        int d = 2 - posY;
        return d;
    }

}
