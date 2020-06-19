package com.garehn.bloodteacher.characters;

public enum StudentSkills {

    HYP ("Hyperactive"), // -1 for attention
    DIS ("Disturbing presence"), //-1 for attention of nearby students
    PET ("Pet"),
    GEN("Genious"),
    FRI("Friendly"); // +1 for attention of nearby students

    private String name = "";

    //Constructor
    StudentSkills(String name){
        this.name = name;
    }


    public String toString(){
        return name;
    }

}
