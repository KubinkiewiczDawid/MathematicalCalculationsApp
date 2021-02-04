import Exceptions.IncorrectDataLength;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class MatrixUtil implements Calculable{
    private double[][] array;

    public MatrixUtil(double[][] array) {
        this.array = array;
    }

    public double[][] getArray() {
        double[][] matrixArray = array;
        return matrixArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatrixUtil matrixUtil = (MatrixUtil) o;
        return Arrays.deepEquals(this.array, matrixUtil.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    //TODO: fix this cases format:
    //    [10 25 5]
    //    [10 0 0]
    //    [5 0 0]
    @Override
    public String toString() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", decimalFormatSymbols);
        StringBuilder matrixString = new StringBuilder();
        for(double[] rows: this.array){
            matrixString.append("[");
            for(int i = 0; i < rows.length; i++){
                matrixString.append(df.format(rows[i]));
                if(i != rows.length - 1) {
                    matrixString.append(" ");
                }
            }
            matrixString.append("]\n");
        }
        return matrixString.toString();
    }

    @Override
    public void makeCalculation(Calculable object) throws IncorrectDataLength {
        if(object instanceof NumberUtil || object instanceof VectorUtil){
            handleNumberVectorCase(object);
        }else{
            handleMatrixCase(object);
        }
    }

    private void handleNumberVectorCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("Multiplication is the only operation allowed here");
            System.out.println("1. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (Main.getUserNumericInput()){
                case 1 -> this.multiply(object);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private void handleMatrixCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("1. Summary");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (Main.getUserNumericInput()){
                case 1 -> this.sum(object);
                case 2 -> this.subtract(object);
                case 3 -> this.multiply(object);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    @Override
    public void multiply(Calculable object) throws IncorrectDataLength {
        if(object instanceof NumberUtil){
            for(int i = 0; i < this.array.length; i++){
                for(int j = 0; j < this.array[i].length; j++){
                    this.array[i][j] *= ((NumberUtil) object).getValue();
                }
            }
        }else if(object instanceof VectorUtil){
            object.multiply(this);
        }
        System.out.println(this);
    }

    @Override
    public void sum(Calculable object) throws IncorrectDataLength {
        int thisColumns = this.array.length;
        int thisValues = this.array[0].length;
        int objectColumns = ((MatrixUtil) object).getArray().length;
        int objectValues = ((MatrixUtil) object).getArray()[0].length;

        if(thisColumns != objectColumns || thisValues != objectValues){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        for (int row = 0; row < thisColumns; row++) {
            for (int column = 0; column < thisValues; column++) {
                this.array[row][column] += ((MatrixUtil) object).getArray()[row][column];
            }
        }
        System.out.println(this);
    }

    @Override
    public void subtract(Calculable object) throws IncorrectDataLength {
        int thisRows = this.array.length;
        int thisColumns = this.array[0].length;
        int objectRows = ((MatrixUtil) object).getArray().length;
        int objectColumns = ((MatrixUtil) object).getArray()[0].length;

        if(thisRows != objectRows || thisColumns != objectColumns){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        for (int row = 0; row < thisRows; row++) {
            for (int column = 0; column < thisColumns; column++) {
                this.array[row][column] -= ((MatrixUtil) object).getArray()[row][column];
            }
        }
        System.out.println(this);
    }
}