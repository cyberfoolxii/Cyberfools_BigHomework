package Application.java;

public class Question {
    private int order;
    private String question;
    private String[] options;
    private char correctAnswer;

    public Question(int order, String question, String[] options, char correctAnswer) {
        this.order = order;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getOrder() {
        return order;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}
