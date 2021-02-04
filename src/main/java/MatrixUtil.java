import java.util.Arrays;
import java.util.Scanner;

public class MatrixUtil implements Calculable{
    private double[][] array;

    public MatrixUtil(double[][] array) {
        this.array = array;
    }

    public MatrixUtil(){}

    public double[][] getArray() {
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
        for(double[] rows: array){
            for(double x : rows){
                if(x % 1 != 0) {
                    matrixString.append(x);
                }else {
                    matrixString.append((int)x);
                }
                matrixString.append(" ");
            }
            matrixString.append("\n");
        }
        return matrixString.toString();
    }

    @Override
    public void makeCalculation(Calculable object) {

    }

    @Override
    public void multiply(Calculable object) {
        if(object instanceof NumberUtil){
            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array[i].length; j++){
                    array[i][j] *= ((NumberUtil) object).getValue();
                }
            }
        }
        System.out.println(this);
    }

    public void printMatrix(){

    }
}
