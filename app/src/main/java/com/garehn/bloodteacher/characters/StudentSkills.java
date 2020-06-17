package com.garehn.bloodteacher.characters;

public enum StudentSkills {

    HYP ("Hyperactive"), // -1 for attention attack
    DIS ("Disturbing presence"), //-1 for attention of nearby students
    PET ("Pet"); //+1 for attention / +2 if distance = 0;

    private String name = "";

    //Constructor
    StudentSkills(String name){
        this.name = name;
    }


    public String toString(){
        return name;
    }

}
