package com.mcsimonflash.daem0ns.forge.triviaquest.objects;

public interface Trivia {

    String getQuestion();

    String getAnswer();

    boolean checkAnswer(String str);
    
    int getDuration();

    boolean showAnswer();

}