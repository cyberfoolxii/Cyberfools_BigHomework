package Application.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionReader {

    public static List<Question> readQuestionsFromFile(String questionsFileName, String answersFileName) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader brQuestions = new BufferedReader(new FileReader(questionsFileName));
             BufferedReader brAnswers = new BufferedReader(new FileReader(answersFileName))) {

            String questionLine;
            String answersLine = brAnswers.readLine();

            int order = 1;
            int count;

            int[] a = new int[4];

            while ((questionLine = brQuestions.readLine()) != null) {
                char correctAnswer = answersLine.charAt(order - 1);

                String optionsLine = brQuestions.readLine();

                if(order < 10) {
                    count = 3;
                } else if(order < 100) {
                    count = 4;
                } else {
                    count = 5;
                }

                String questionText = questionLine.substring(count).trim();

                a[0] = optionsLine.indexOf("A. ");
                a[1] = optionsLine.indexOf("B. ");
                a[2] = optionsLine.indexOf("C. ");
                a[3] = optionsLine.indexOf("D. ");

                String[] options = new String[4];

                for (int i = 0; i < 3; i++) {
                    options[i] = optionsLine.substring(a[i], a[i + 1] - 1).trim();
                }

                options[3] = optionsLine.substring(a[3]).trim();

                Question question = new Question(order, questionText, options, correctAnswer);
                questions.add(question);
                order++;
                System.out.println(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
