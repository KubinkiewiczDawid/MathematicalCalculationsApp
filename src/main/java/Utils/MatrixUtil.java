package Utils;

import Exceptions.IncorrectDataLength;
import Handlers.DataHandler;
import UserCommunication.UserInput;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class MatrixUtil implements Calculable {
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

    @Override
    public void makeCalculation(Calculable object) throws IncorrectDataLength {
        if(object instanceof NumberUtil || object instanceof VectorUtil){
            handleNumberVectorCase(object);
        }else{
            handleMatricesCase(object);
        }
    }

    private void handleNumberVectorCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("Multiplication is the only operation allowed here");
            System.out.println("1. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (UserInput.getUserNumericInput()){
                case 1 -> this.multiply(object);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private void handleMatricesCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("1. Summary");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (UserInput.getUserNumericInput()){
                case 1 -> DataHandler.writeCalculation(this, object, this.sum(object), '+');
                case 2 -> DataHandler.writeCalculation(this, object, this.subtract(object), '-');
                case 3 -> DataHandler.writeCalculation(this, object, this.multiply(object), '*');
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    @Override
    public Calculable multiply(Calculable object) throws IncorrectDataLength {
        MatrixUtil matrixUtil = new MatrixUtil(this.array);
        if(object instanceof NumberUtil){
            double[][] result = new double[matrixUtil.array.length][matrixUtil.array[0].length];
            for(int i = 0; i < matrixUtil.array.length; i++){
                result[i] = this.array[i].clone();
            }

            for(int i = 0; i < result.length; i++){
                for(int j = 0; j < result[i].length; j++){
                    result[i][j] *= ((NumberUtil) object).getValue();
                }
            }
            matrixUtil.array = result;
        }else if(object instanceof VectorUtil){
            return object.multiply(this);
        }else if(object instanceof MatrixUtil){
            int rows = ((MatrixUtil) object).getArray().length;
            int columns = ((MatrixUtil) object).getArray()[0].length;

            if(matrixUtil.array.length != columns){
                throw new IncorrectDataLength("First matrices row must have same length as second matrices columns");
            }

            double[][] result = new double[rows][columns];

            for(int row=0;row<rows;row++){
                for(int column=0;column<columns;column++){
                    for(int x=0;x<columns;x++)
                    {
                        result[row][column]+=matrixUtil.array[row][x]*((MatrixUtil) object).getArray()[x][column];
                    }
                }
            }

            matrixUtil.array = result;
        }
        return this;
    }

    @Override
    public Calculable sum(Calculable object) throws IncorrectDataLength {
        MatrixUtil matrixUtil = new MatrixUtil(this.array);
        int thisColumns = matrixUtil.array.length;
        int thisValues = matrixUtil.array[0].length;
        int objectColumns = ((MatrixUtil) object).getArray().length;
        int objectValues = ((MatrixUtil) object).getArray()[0].length;

        if(thisColumns != objectColumns || thisValues != objectValues){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        for (int row = 0; row < thisColumns; row++) {
            for (int column = 0; column < thisValues; column++) {
                matrixUtil.array[row][column] += ((MatrixUtil) object).getArray()[row][column];
            }
        }
        return this;
    }

    @Override
    public Calculable subtract(Calculable object) throws IncorrectDataLength {
        MatrixUtil matrixUtil = new MatrixUtil(this.array);
        int thisRows = matrixUtil.array.length;
        int thisColumns = matrixUtil.array[0].length;
        int objectRows = ((MatrixUtil) object).getArray().length;
        int objectColumns = ((MatrixUtil) object).getArray()[0].length;

        if(thisRows != objectRows || thisColumns != objectColumns){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        for (int row = 0; row < thisRows; row++) {
            for (int column = 0; column < thisColumns; column++) {
                matrixUtil.array[row][column] -= ((MatrixUtil) object).getArray()[row][column];
            }
        }
        return this;
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
        for(int row = 0; row < this.array.length; row++){
            matrixString.append("[");
            for(int i = 0; i < this.array[row].length; i++){
                matrixString.append(df.format(this.array[row][i]));
                if(i != this.array[row].length - 1) {
                    matrixString.append(" ");
                }
            }
            matrixString.append("]");
            if(row != this.array.length - 1) {
                matrixString.append("\n");
            }
        }
        return matrixString.toString();
    }
}