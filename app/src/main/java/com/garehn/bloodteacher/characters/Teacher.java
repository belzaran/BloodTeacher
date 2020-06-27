package com.garehn.bloodteacher.characters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.garehn.bloodteacher.gameplay.Attack;
import com.garehn.bloodteacher.gameplay.Dice;

public class Teacher {

    protected String name;
    protected int charisma;
    protected int pedagogy;
    protected int armor;
    protected int value;
    protected int max = 6;
    protected int min = 0;
    protected Dice dice = new Dice();

    protected Attack lastAttack;

    private String DICE_RESULT ="DE %s (%s+)";
    private String NEED_RESULT ="need : %s";

    public Teacher(String name, int charisma, int pedagogy, int armor) {
        this.name = "Mister" + name;
        this.charisma = charisma;
        this.pedagogy = pedagogy;
        this.armor = armor;
        lastAttack = new Attack();
    }

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    public Teacher(){
        this.name = "Mister Player";
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
        init();
    }

    public Teacher(String n){
        this.name = "Mister " + n;
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
        init();
    }

    public void init(){
        lastAttack = new Attack();
        this.charisma = verifyValue(charisma, min, max);
        this.pedagogy = verifyValue(pedagogy,min, max);
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public int getValue() {
        value = (int) ((charisma*100 + pedagogy*100)*1.5);
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

    public int getCharisma() {
        return charisma;
    }

    public Attack getLastAttack() {
        return lastAttack;
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

    public boolean attackStudent(Student student, int fBonus){

                boolean b = false;
                int mark = 0;

            Log.i("TEACH_TEACHER", "ATTACKING " + student.getName());
            int fNeed = verifyValue(focusNeed(student, fBonus), 2, 6);
            int fDice = dice.rollDice(6);
            int aNeed = verifyValue(attackNeed(student, fDice), 2, 6);
            int aDice = dice.rollDice(6);
            Log.i("TEACH_TEACHER", String.format(DICE_RESULT, fDice, fNeed));

            if (fDice >= fNeed) {
                lastAttack.setfResult(true);
                Log.i("TEACH_TEACHER", String.format(DICE_RESULT, aDice, aNeed));

                if (aDice >= aNeed) {
                    lastAttack.setaResult(true);
                    b = true;
                    if(aDice == 6){
                        mark = 2;
                    }
                    else{
                        mark = 1;
                    }

                } else {
                    lastAttack.setaResult(false);
                    if (aDice == 0) {
                        mark = -1;
                    }
                }
            }
            else {
                lastAttack.setfResult(false);
            }

            Log.i("TEACH_TEACHER", "Attack " + b);
            // student can't be attacked at this round again
        student.addMark(mark);
        student.setPlayable(false);
        lastAttack.setfNeed(fNeed);
        lastAttack.setaNeed(aNeed);
        lastAttack.setfDice(fDice);
        lastAttack.setaDice(aDice);
        lastAttack.setStudent(student);
        lastAttack.setMarkChange(mark);

        return b;
    }

    public int focusNeed(Student student, int bonus){
        int need = 0;
        Log.i("TEACH_TEACHER", "Position bonus : " + bonus);
        if(student.getSkills().contains(StudentSkills.HYP)){
            bonus -= 1 ;
        }
        if(student.getSkills().contains(StudentSkills.PET)){
            bonus +=1;
            }

        need = 9 - this.charisma - student.getFocus() + student.getDistance() - bonus;
        need = verifyValue(need, 2, 6);
        return need;
    }

    public int attackNeed(Student student, int dice){
            int need;
            int bonus = 0;

            if(dice==6){ //Critical attention
                bonus +=1;
            }
            need = 9 - this.pedagogy - student.getIntelligence() + student.getDistance() - bonus;
            need = verifyValue(need, 2, 6);
            return need;
        }

    public int verifyValue(int val, int mn, int mx) {
        if (val > mx) {
            val = mx;
        } else if (val < mn) {
            val = mn;
        }
        Log.i("TEACH_TEACHER", String.format(NEED_RESULT,val));
        return val;
    }

    public int getProbability(Student student, int bonus){
        float fProba = 6 - focusNeed(student, bonus) + 1;
        float aProba = attackNeed(student, 3)*5 + attackNeed(student, 6);
        aProba = 6 - (aProba/6) + 1;
        float proba =  (fProba/6)*(aProba/6)*100;
        Log.i("TEACH_TEACHER", "Proba : " + fProba + " - " + aProba + " - " + (int)proba);
        return (int) proba;
    }

}




