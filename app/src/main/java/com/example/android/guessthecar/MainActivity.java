package com.example.android.guessthecar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button resetButton;
    private List<Answer> allAnswers = new ArrayList();
    private List<Question> questions = new ArrayList();
    private Random random = new Random();
    private int currentQuestionLocation = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetButton = findViewById(R.id.button_main_reset);
        resetButton.setVisibility(View.INVISIBLE);

        populateAllAnswers();
        populateQuestions();
        displayQuestion(0);
        displayScore();
        displayQuestionsLeft();
        enableAnswerButtons(true);
    }

    public void reset(View view) {
        resetButton.setVisibility(View.INVISIBLE);
        currentQuestionLocation = 0;
        score = 0;
        populateQuestions();
        displayQuestion(0);
        displayScore();
        displayQuestionsLeft();
        enableAnswerButtons(true);
    }

    public void validateAnswer(View view) {
        if (currentQuestionLocation < questions.size() - 1) {

            Question currentQuestion = questions.get(currentQuestionLocation);

            boolean isValid = false;
            switch (view.getId()) {
                case R.id.button_main_answer_0:
                    isValid = currentQuestion.getCorrectAnswerLocation() == 0;
                    break;
                case R.id.button_main_answer_1:
                    isValid = currentQuestion.getCorrectAnswerLocation() == 1;
                    break;
                case R.id.button_main_answer_2:
                    isValid = currentQuestion.getCorrectAnswerLocation() == 2;
                    break;
                default:
                    isValid = currentQuestion.getCorrectAnswerLocation() == 3;
                    break;
            }

            String message;
            if (isValid) {
                score++;
                message = getString(R.string.main_correct_message);
            } else {
                message = getString(R.string.main_incorrect_message);
            }

            displayScore();

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            displayNextQuestion();

            if (currentQuestionLocation == questions.size() - 1) {
                resetButton.setVisibility(View.VISIBLE);
                enableAnswerButtons(false);
            }
        }
    }

    private void enableAnswerButtons(boolean value) {
        Button answer0Button = findViewById(R.id.button_main_answer_0);
        answer0Button.setEnabled(value);

        Button answer1Button = findViewById(R.id.button_main_answer_1);
        answer1Button.setEnabled(value);

        Button answer2Button = findViewById(R.id.button_main_answer_2);
        answer2Button.setEnabled(value);

        Button answer3Button = findViewById(R.id.button_main_answer_3);
        answer3Button.setEnabled(value);
    }

    private void displayScore() {
        TextView scoreTextView = findViewById(R.id.textView_main_score);
        scoreTextView.setText(getString(R.string.main_score_label, score));
    }

    private void displayQuestionNo() {
        TextView questionNoTextView = findViewById(R.id.textView_main_question_no);
        questionNoTextView.setText(getString(R.string.main_question_label) + " " + (currentQuestionLocation + 1));
    }

    private void displayQuestionsLeft() {
        TextView questionsLeftTextView = findViewById(R.id.textView_main_questions_left);
        questionsLeftTextView.setText(getString(R.string.main_questions_left_label, (questions.size() - currentQuestionLocation - 1)));
    }

    private void displayQuestion(int location) {
        displayQuestionNo();
        displayQuestionsLeft();

        TextView questionsLeftTextView = findViewById(R.id.textView_main_questions_left);
        questionsLeftTextView.setText(getString(R.string.main_questions_left_label, (questions.size() - location - 1)));

        Question question = questions.get(location);
        Answer[] answers = question.getAnswers();

        ImageView carImageView = findViewById(R.id.imageView_main_car);
        carImageView.setImageResource(answers[question.getCorrectAnswerLocation()].getCarImageResId());

        displayAnswer(answers[0], R.id.imageView_main_answer0_logo, R.id.button_main_answer_0);
        displayAnswer(answers[1], R.id.imageView_main_answer1_logo, R.id.button_main_answer_1);
        displayAnswer(answers[2], R.id.imageView_main_answer2_logo, R.id.button_main_answer_2);
        displayAnswer(answers[3], R.id.imageView_main_answer3_logo, R.id.button_main_answer_3);
    }

    private void displayAnswer(Answer answer, int logoResId, int buttonResId) {
        ImageView answerLogo = findViewById(logoResId);
        answerLogo.setImageResource(answer.getBrandImageResId());

        Button answerButton = findViewById(buttonResId);
        answerButton.setText(answer.getMessage());
    }

    private void displayNextQuestion() {
        displayQuestion(++currentQuestionLocation);
    }

    private void populateQuestions() {
        questions.clear();

        for (int i = 0; i < allAnswers.size(); i++) {
            Answer[] questionAnswers = new Answer[4];
            int correctAnswerLocation = random.nextInt(4);
            questionAnswers[correctAnswerLocation] = allAnswers.get(i);
            List<Integer> questionAnswerLocations = new ArrayList<>();
            questionAnswerLocations.add(i);

            for (int j = 0; j < 4; j++) {
                if (j != correctAnswerLocation) {
                    int answerLocation = i;

                    while (questionAnswerLocations.contains(answerLocation)) {
                        answerLocation = random.nextInt(10);
                    }

                    questionAnswers[j] = allAnswers.get(answerLocation);
                    questionAnswerLocations.add(answerLocation);
                }
            }

            questions.add(new Question(questionAnswers, correctAnswerLocation));
        }

        shuffleQuestions();
    }

    private void shuffleQuestions() {
        for (int i = 0; i < questions.size(); i++) {
            int quetionToSwapWithLocation = random.nextInt(questions.size());

            swapQuestions(i, quetionToSwapWithLocation);
        }
    }

    private void swapQuestions(int location1, int location2) {
        Question temp = questions.get(location1);
        questions.set(location1, questions.get(location2));
        questions.set(location2, temp);
    }

    private void populateAllAnswers() {
        allAnswers.add(new Answer(R.drawable.main_question_car_image1,
                R.drawable.main_question_manufacturer_logo1, "DB5"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image2,
                R.drawable.main_question_manufacturer_logo2, "Coupe VR-T Vorsteiner"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image3,
                R.drawable.main_question_manufacturer_logo3, "Cougar Eliminator"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image4,
                R.drawable.main_question_manufacturer_logo4, "Cooper S JCW GP"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image5,
                R.drawable.main_question_manufacturer_logo5, "Esprit"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image6,
                R.drawable.main_question_manufacturer_logo6, "Diablo GTR"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image7,
                R.drawable.main_question_manufacturer_logo7, "GT40"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image8,
                R.drawable.main_question_manufacturer_logo8, "Viper SRT10"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image9,
                R.drawable.main_question_manufacturer_logo9, "BMW M1"));

        allAnswers.add(new Answer(R.drawable.main_question_car_image10,
                R.drawable.main_question_manufacturer_logo9, "3.0 CSL"));
    }
}
