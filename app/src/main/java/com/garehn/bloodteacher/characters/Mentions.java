package com.garehn.bloodteacher.characters;

public enum Mentions {

    VERYGOOD ("Very good"), // A
    GOOD ("Good"), // B
    BAD("Bad"); // For F students

    private String name = "";

    //Constructor
    Mentions(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
