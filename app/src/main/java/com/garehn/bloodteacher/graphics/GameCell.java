package com.garehn.bloodteacher.graphics;

public class GameCell {



    protected int posX;
    protected int posY;
    protected boolean playable;
    protected boolean student;

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

           public GameCell() {
            posX = 0;
            posY = 0;
            student = false;
            playable = true;
        }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public boolean hasStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }


}

