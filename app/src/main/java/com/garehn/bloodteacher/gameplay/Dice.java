package com.garehn.bloodteacher.gameplay;

public class Dice {

    public Dice(){}

    public int rollDice(int i){// i = number of values of the dice
        int d = (int) (Math.random() * (double) (i - 1)) + 1;
        return d;
    }

}
