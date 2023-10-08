package Application.java.CustomExceptions;

public class InappropriateInputException extends RuntimeException{
    public InappropriateInputException() {
        super("Wrong word input!");
    }
    public InappropriateInputException(String s) {
        super(s);
    }
}
