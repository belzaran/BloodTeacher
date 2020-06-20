package com.garehn.bloodteacher.gameplay;

public enum Phase {

    PLAC ("Placement"), // The player can choose the place of the students
    PLAY ("Play"), // Player can play
    NAME ("Name"), // Player can choose his name
    PET ("Pet"), // Player can choose his pet
    WAIT ("Wait"), // Player can do anything on screen
    MOVE("Move"); // Player can move a student

    private String name = "";

    //Constructor
    Phase(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
