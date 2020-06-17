package com.garehn.bloodteacher.characters;

public enum TeacherSkills {

    EYE ("Death glare"), // isolating a student - no influence of other students
    VOI ("Strong voice"); // can reroll the dice if attention failure

    private String name = "";

    //Constructor
    TeacherSkills(String name){
        this.name = name;
    }


    public String toString(){
        return name;
    }

}
