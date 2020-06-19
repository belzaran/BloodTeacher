package com.garehn.bloodteacher.characters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Student {

    protected String name;
    protected int mark; // 0 to 20
    protected int intelligence; // 0 to 6
    protected int focus; // 0 to 6
    protected int insolence; // 0 to 6
    protected int posX; // 0 to 4
    protected int posY; // 0 to 3
    protected ArrayList<String> names = new ArrayList<>();
    protected boolean selected = false;
    protected boolean playable = true;
    protected ArrayList<StudentSkills> skills = new ArrayList<>();
    protected ArrayList<String> chosenNames = new ArrayList<>();

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public Student(){
        init();
        this.name = "student";
        this.mark = 10;
        this.intelligence = 3;
        this.insolence = 3;
        this.focus = 3;
        this.posX = 0;
        this.posY = 0;
    }

    public Student(int intelligence, int focus, int mark){
        init();
        this.name = "student";
        this.mark = mark;
        this.intelligence = intelligence;
        this.insolence = 3;
        this.focus = focus;
        this.posX = 0;
        this.posY = 0;
    }

    public Student(String n){
        init();
        this.name = n;
        this.mark = 10;
        this.intelligence = 3;
        this.insolence = 3;
        this.focus = 3;
        this.posX = 0;
        this.posY = 0;

    }

    public Student(String name, int mark, int intelligence, int focus, int insolence, int posX, int posY) {
        init();
        this.name = name;
        this.mark = mark;
        this.intelligence = intelligence;
        this.focus = focus;
        this.insolence = insolence;
        this.posX = posX;
        this.posY = posY;

    }

    public void init(){
        generateNames();
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
        int d = 2 - posY;
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
}


