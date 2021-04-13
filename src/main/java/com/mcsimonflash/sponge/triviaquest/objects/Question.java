package com.mcsimonflash.sponge.triviaquest.objects;

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
        return "The answer" + (Answers.size() > 1 ? "s were " : " was ") + String.join(", ", Answers);
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
