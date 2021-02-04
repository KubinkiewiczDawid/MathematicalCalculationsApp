package Exceptions;

public class TooBigMatrixException extends Exception{
    public TooBigMatrixException() {
        super("Matrix is too big, maximum size of matrix is 4x4");
    }
}
