package Exceptions;

public class InsufficientAmountOfDataException extends Exception{
    public InsufficientAmountOfDataException() {
        super("Insufficient amount of data entered by the user");
    }
}