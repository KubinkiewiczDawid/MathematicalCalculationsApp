import java.util.Arrays;
import java.util.Scanner;

public class MatrixUtil implements Calculable{
    private int columns;
    private int rows;
    private int array[][];

    public MatrixUtil(int[][] array) {
        this.array = array;
    }

    public MatrixUtil(){}

    public int[][] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixUtil matrixUtil = (MatrixUtil) o;
        return Arrays.deepEquals(array, matrixUtil.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        StringBuilder matrixString = new StringBuilder();
        for(int[] rows: array){
            for(int x : rows){
                matrixString.append(x);
                matrixString.append(" ");
            }
            matrixString.append("\n");
        }
        return matrixString.toString();
    }

    @Override
    public void makeCalculation(Object object) {

    }
}
