package Exceptions;

public class IncorrectInputDataException extends Exception{
    public IncorrectInputDataException() {
        super("Incorrect data entered by the user");
    }
}