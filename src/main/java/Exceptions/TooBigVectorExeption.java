package Exceptions;

public class TooBigVectorExeption extends Exception{
    public TooBigVectorExeption() {
        super("Vector is too big, maximum length of vector is 4");
    }
}