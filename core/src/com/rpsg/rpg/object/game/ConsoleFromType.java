package com.rpsg.rpg.object.game;


public enum ConsoleFromType {
    Console("aaaaaa"),
    LogInfo("663399"),
    LogDebug("339990"),
    LogError("ff1818"),
    Executed("497793")
    ;

    private String color;
    private ConsoleFromType(String color){
        this.color = color;
    }

    public String color(){
        return color;
    }
}
