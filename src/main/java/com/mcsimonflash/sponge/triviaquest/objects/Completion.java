package com.mcsimonflash.sponge.triviaquest.objects;

import com.mcsimonflash.sponge.triviaquest.managers.Config;
import com.mcsimonflash.sponge.triviaquest.managers.Util;

import java.util.List;

public class Completion implements Trivia {

    public String Word;
    public List<String> Choices;
    public int Duration;
    public boolean ShowAnswer;

    public Completion(String word, List<String> choices, int duration, boolean showAnswer) {
        Word = word;
        Choices = choices;
        Duration = duration;
        ShowAnswer=showAnswer;
    }

    @Override
    public String getQuestion() {
        return Config.completionQuestion.replace("<word>", Choices.isEmpty() ? Util.getCompletion(Word) : Choices.get(Util.random.nextInt(Choices.size())));
    }

    @Override
    public String getAnswer() {
        return Config.completionAnswer.replace("<word>", Word);
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
