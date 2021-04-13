package com.mcsimonflash.sponge.triviaquest.objects;

public interface Trivia {

    String getQuestion();

    String getAnswer();

    boolean checkAnswer(String str);
    
    int getDuration();

    boolean showAnswer();

}