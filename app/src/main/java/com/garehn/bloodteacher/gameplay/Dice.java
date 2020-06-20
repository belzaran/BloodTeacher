package com.garehn.bloodteacher.gameplay;

import java.util.Random;

public class Dice {

    public Dice(){}

    public static final Random RANDOM = new Random();

    public int rollDice(int i){// i = number of values of the dice
        //int d = (int) (Math.random() * (double) (i - 1)) + 1;

        return RANDOM.nextInt(6)+1;
    }

    public int rollDiceWithIntervals(int mn, int mx){
        return RANDOM.nextInt(mx)+mn;
    }

}
