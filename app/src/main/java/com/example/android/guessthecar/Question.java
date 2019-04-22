package com.example.android.guessthecar;

public class Question {
    public Question(Answer[] answers, int correctAnswerLocation) {
        this.answers = answers;
        this.correctAnswerLocation = correctAnswerLocation;
    }

    private int correctAnswerLocation;

    public int getCorrectAnswerLocation() {
        return correctAnswerLocation;
    }

    private Answer[] answers;

    public Answer[] getAnswers() {
        return answers;
    }
}
