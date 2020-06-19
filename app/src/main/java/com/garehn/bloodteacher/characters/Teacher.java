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
    protected Dice dice = new Dice();

    protected Attack lastAttack;

    private String DICE_RESULT ="DE %s (%s+)";

    public Teacher(String name, int charisma, int pedagogy, int armor) {
        this.name = "Mister" + name;
        this.charisma = charisma;
        this.pedagogy = pedagogy;
        this.armor = armor;
        lastAttack = new Attack();
    }

    public Teacher(){
        this.name = "Mister Player";
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
        lastAttack = new Attack();
    }

    public Teacher(String n){
        this.name = "Mister " + n;
        this.charisma = 3;
        this.pedagogy = 3;
        this.armor = 3;
        lastAttack = new Attack();
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

    public boolean attackStudent(Student student){

                boolean b = false;

                int mark = 0;

            Log.i("TEACH_TEACHER", "ATTACKING " + student.getName());
            int fNeed = focusNeed(student);
            int fDice = dice.rollDice(6);
            int aNeed = attackNeed(student, fDice);
            int aDice = dice.rollDice(6);
            Log.i("TEACH_TEACHER", String.format(DICE_RESULT, fDice, fNeed));

            if (fDice >= fNeed) {
                lastAttack.setfResult(true);
                Log.i("TEACH_TEACHER", String.format(DICE_RESULT, aDice, aNeed));

                if (aDice >= aNeed) {
                    lastAttack.setaResult(true);
                    b = true;
                    if(aDice == 6){
                        mark = 4;
                    }
                    else{
                        mark = 2;
                    }


                } else {
                    lastAttack.setaResult(false);
                    if (aDice == 0) {
                        mark = -2;
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

    public int focusNeed(Student student){
        int need = 0;
        int bonus = 0;
        if(student.getSkills().contains(StudentSkills.HYP)){
            bonus -= 1 ;
        }
        if(student.getSkills().contains(StudentSkills.PET)){
            bonus +=1;

            }

        need = 9 - this.charisma - student.getFocus() + student.getDistance() - bonus;
        return need;
    }

    public int attackNeed(Student student, int dice){
            int need = 0;
            int bonus = 0;

            if(dice==6){ //Critical attention
                bonus +=1;
            }

            need = 9 - this.pedagogy - student.getIntelligence() + student.getDistance() - bonus;
            return need;
        }

}




