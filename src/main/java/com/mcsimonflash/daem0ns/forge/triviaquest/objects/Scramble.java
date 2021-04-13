package com.mcsimonflash.daem0ns.forge.triviaquest.objects;

import java.util.List;

import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Config;
import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Util;

public class Scramble implements Trivia {

    public String Word;
    public List<String> Choices;
    public int Duration;
    public boolean ShowAnswer;

    public Scramble(String word, List<String> choices, int duration, boolean showAnswer) {
        Word = word;
        Choices = choices;
        Duration = duration;
        ShowAnswer=showAnswer;
    }

    @Override
    public String getQuestion() {
        return Config.scrambleQuestion.replace("<word>", Choices.isEmpty() ? Util.getScramble(Word) : Choices.get(Util.random.nextInt(Choices.size())));
    }

    @Override
    public String getAnswer() {
        return Config.scrambleAnswer.replace("<word>", Word);
    }

    @Override
    public boolean checkAnswer(String str) {
        return str.equalsIgnoreCase(Word);
    }

	@Override
	public int getDuration() {
		return Duration;
	}

	@Override
	public boolean showAnswer() {
		return ShowAnswer;
	}
}
