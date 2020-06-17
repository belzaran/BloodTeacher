package com.garehn.bloodteacher.graphics;

public class GameCell {
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }



    protected int posX;
    protected int posY;

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTORS
    ----------------------------------------------------------------------------------------------*/

    protected boolean bStudent;

        public GameCell() {
            posX = 0;
            posY = 0;
            bStudent = false;
        }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public boolean hasStudent() {
        return bStudent;
    }

    public void setStudent(boolean bStudent) {
        this.bStudent = bStudent;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

}

