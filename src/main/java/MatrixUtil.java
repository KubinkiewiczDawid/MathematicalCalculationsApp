import Exceptions.IncorrectDataLength;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class MatrixUtil implements Calculable{
    private double[][] array;
    private final DataHandler dataHandler;

    public MatrixUtil(double[][] array) {
        this.array = array;
        this.dataHandler = Main.dataHandler;
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

            switch (Main.getUserNumericInput()){
                case 1 -> this.multiply(object, this.dataHandler);
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

            switch (Main.getUserNumericInput()){
                case 1 -> this.sum(object, this.dataHandler);
                case 2 -> this.subtract(object, this.dataHandler);
                case 3 -> this.multiply(object, this.dataHandler);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    @Override
    public Calculable multiply(Calculable object, DataHandler dataHandler) throws IncorrectDataLength {
        if(object instanceof NumberUtil){
            double[][] result = new double[this.array.length][this.array[0].length];
            for(int i = 0; i < this.array.length; i++){
                result[i] = this.array[i].clone();
            }

            for(int i = 0; i < result.length; i++){
                for(int j = 0; j < result[i].length; j++){
                    result[i][j] *= ((NumberUtil) object).getValue();
                }
            }
            dataHandler.writeCalculationObjects(this, object, '*');
            this.array = result;
        }else if(object instanceof VectorUtil){
            object.multiply(this, dataHandler);
        }else if(object instanceof MatrixUtil){
            int rows = ((MatrixUtil) object).getArray().length;
            int columns = ((MatrixUtil) object).getArray()[0].length;

            if(this.array.length != columns){
                throw new IncorrectDataLength("First matrices row must have same length as second matrices columns");
            }

            double[][] result = new double[rows][columns];

            for(int row=0;row<rows;row++){
                for(int column=0;column<columns;column++){
                    for(int x=0;x<columns;x++)
                    {
                        result[row][column]+=this.array[row][x]*((MatrixUtil) object).getArray()[x][column];
                    }
                }
            }
            dataHandler.writeCalculationObjects(this, object, '*');

            this.array = result;
        }
        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable sum(Calculable object, DataHandler dataHandler) throws IncorrectDataLength {
        int thisColumns = this.array.length;
        int thisValues = this.array[0].length;
        int objectColumns = ((MatrixUtil) object).getArray().length;
        int objectValues = ((MatrixUtil) object).getArray()[0].length;

        if(thisColumns != objectColumns || thisValues != objectValues){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        dataHandler.writeCalculationObjects(this, object, '+');

        for (int row = 0; row < thisColumns; row++) {
            for (int column = 0; column < thisValues; column++) {
                this.array[row][column] += ((MatrixUtil) object).getArray()[row][column];
            }
        }
        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable subtract(Calculable object, DataHandler dataHandler) throws IncorrectDataLength {
        int thisRows = this.array.length;
        int thisColumns = this.array[0].length;
        int objectRows = ((MatrixUtil) object).getArray().length;
        int objectColumns = ((MatrixUtil) object).getArray()[0].length;

        if(thisRows != objectRows || thisColumns != objectColumns){
            throw new IncorrectDataLength("Matrix must be the same size");
        }

        dataHandler.writeCalculationObjects(this, object, '+');

        for (int row = 0; row < thisRows; row++) {
            for (int column = 0; column < thisColumns; column++) {
                this.array[row][column] -= ((MatrixUtil) object).getArray()[row][column];
            }
        }
        dataHandler.writeCalculationResult(this);
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