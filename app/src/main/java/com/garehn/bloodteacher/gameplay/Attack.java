package com.garehn.bloodteacher.gameplay;

import com.garehn.bloodteacher.characters.Student;
import com.garehn.bloodteacher.characters.Teacher;

public class Attack {

    protected Student student;
    protected boolean bAttention = false;
    private String ATTACK_MESSAGE = "Teaching %s";
    private String ATTENTION_MESSAGE = "Attention : DE %s (%s+) | %s";
    private String TEACHING_MESSAGE = "Teaching : DE %s (%s+) | %s";
    private String RESULT_MESSAGE = "Mark : +%s (%s/20)";
    private boolean bTeaching = false;
    private int result = 0;
    private int fDice = 0;
    private int aDice = 0;
    private boolean fResult = false;
    private int fNeed = 0;
    private int aNeed = 0;
    private boolean aResult = true;
    private int markChange;

    public Attack(){
        student = new Student();
    }

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/

    public String getAttackToast(){
        return(String.format(ATTACK_MESSAGE,student.getName()));
    }

    public String getAttentionToast(){
        String result = "Failed";
        if (fResult){
            result = "Succeed";
        }
        return(String.format(ATTENTION_MESSAGE,fDice, fNeed, result));
    }

    public String getTeachingToast(){
        String result = "Failed";
        if (aResult){
            result = "Succeed";
        }
        return(String.format(TEACHING_MESSAGE,aDice, aNeed, result));
    }

    public String getResultToast(){
        return(String.format(RESULT_MESSAGE,markChange, student.getMark()));
    }


    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public void setMarkChange(int markChange) {
        this.markChange = markChange;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isbAttention() {
        return bAttention;
    }

    public void setbAttention(boolean bAttention) {
        this.bAttention = bAttention;
    }

    public boolean isbTeaching() {
        return bTeaching;
    }

    public void setbTeaching(boolean bTeaching) {
        this.bTeaching = bTeaching;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getfDice() {
        return fDice;
    }

    public void setfDice(int fDice) {
        this.fDice = fDice;
    }

    public int getaDice() {
        return aDice;
    }

    public void setaDice(int aDice) {
        this.aDice = aDice;
    }

    public int getfNeed() {
        return fNeed;
    }

    public void setfNeed(int fNeed) {
        this.fNeed = fNeed;
    }

    public int getaNeed() {
        return aNeed;
    }

    public void setaNeed(int aNeed) {
        this.aNeed = aNeed;
    }

    public boolean isfResult() {
        return fResult;
    }

    public void setfResult(boolean fResult) {
        this.fResult = fResult;
    }

    public boolean isaResult() {
        return aResult;
    }

    public void setaResult(boolean aResult) {
        this.aResult = aResult;
    }

}
