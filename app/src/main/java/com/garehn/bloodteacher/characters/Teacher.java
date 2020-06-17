package com.garehn.bloodteacher.characters;

import android.util.Log;

import com.garehn.bloodteacher.gameplay.Dice;

public class Teacher {

    protected String name;
    protected int charisma;
    protected int pedagogy;
    protected int armor;
    protected Dice dice = new Dice();

    private String DICE_RESULT ="DE %s (%s+)";

    public Teacher(String name, int charisma, int pedagogy, int armor) {
        this.name = "Mister" + name;
        this.charisma = charisma;
        this.pedagogy = pedagogy;
        this.armor = armor;
    }

    public Teacher(){
        this.name = "Mister Player";
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
    }

    public Teacher(String n){
        this.name = "Mister " + n;
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getPedagogy() {
        return pedagogy;
    }

    public void setPedagogy(int pedagogy) {
        this.pedagogy = pedagogy;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    public boolean attackStudent(Student student){
        boolean b = false;
        Log.i("TEACH_TEACHER","ATTACKING " + student.getName());
        int fNeed = focusNeed(student);
        int fDice = dice.rollDice(6);
        Log.i("TEACH_TEACHER",String.format(DICE_RESULT, fDice,fNeed));

        if (fDice >= fNeed){

            int aNeed = attackNeed(student);
            int aDice = dice.rollDice(6);
            Log.i("TEACH_TEACHER",String.format(DICE_RESULT, aDice,aNeed));

            if(aDice>= aNeed){
                b = true;

            }
            else{

            }

        }
        else{
        }
        Log.i("TEACH_TEACHER","Attack " + b);
        return b;
    }

    public int focusNeed(Student student){
        int need = 0;
        int malus = 0;
        if(student.getSkills().contains(StudentSkills.HYP)){
            malus =+1 ;
        }
        need = 9 - this.charisma - student.getFocus() + student.getDistance();
        return need;
    }

    public int attackNeed(Student student){
            int need = 0;
            need = 9 - this.pedagogy - student.getIntelligence() + student.getDistance();
            return need;
        }
    }




