package com.mcsimonflash.daem0ns.forge.triviaquest.objects;

import com.mcsimonflash.daem0ns.forge.triviaquest.managers.Config;

import java.util.List;

public class Question implements Trivia {

    public String Question;
    public List<String> Answers;
    public int Duration;
    public boolean ShowAnswer;

    public Question(String question, List<String> answers, int duration, boolean showAnswer) {
        Question = question;
        Answers = answers;
        Duration = duration;
        ShowAnswer=showAnswer;
    }

    @Override
    public String getQuestion() {
        return Question;
    }

    @Override
    public String getAnswer() {
        return (Answers.size() > 1 ? Config.severalAnswerDisplay : Config.oneAnswerDisplay) + String.join(", ", Answers);
    }

    @Override
    public boolean checkAnswer(String str) {
        return Answers.stream().anyMatch(a -> a.equalsIgnoreCase(str));
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
