package Utils;

import Exceptions.IncorrectDataLength;
import Handlers.DataHandler;
import UserCommunication.UserInput;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

public class VectorUtil implements Calculable {
    private double[] array;
    private final DataHandler dataHandler;
    private UserInput userInput;

    public VectorUtil(double[] array, DataHandler dataHandler) {
        this.array = array;
        this.dataHandler = dataHandler;
        this.userInput = new UserInput();
    }

    @Override
    public void makeCalculation(Calculable object) throws IncorrectDataLength {
        if(object instanceof VectorUtil){
            handleTwoVectorsCase(object);
        }else if(object instanceof NumberUtil || object instanceof MatrixUtil){
            handleVectorNumberMatrixCase(object);
        }
    }

    private void handleTwoVectorsCase(Calculable object) {
        boolean shouldContinue = false;

        do{
            System.out.println("1. Summary");
            System.out.println("2. Subtraction");
            System.out.println();
            System.out.println("0. Leave");

            switch (userInput.getUserNumericInput()){
                case 1 -> this.sum(object, this.dataHandler);
                case 2 -> this.subtract(object, this.dataHandler);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private void handleVectorNumberMatrixCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("Multiplication is the only operation allowed here");
            System.out.println("1. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (userInput.getUserNumericInput()){
                case 1 -> this.multiply(object, this.dataHandler);
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
        dataHandler.writeCalculationObjects(this, object, '*');
        if(object instanceof NumberUtil){
            for(int i = 0; i < this.array.length; i++){
                this.array[i] *= ((NumberUtil) object).getValue();
            }
        }else if(object instanceof MatrixUtil){
            int rows = ((MatrixUtil) object).getArray().length;
            int columns = ((MatrixUtil) object).getArray()[0].length;

            if(this.array.length != rows){
                throw new IncorrectDataLength("Vector must have same length as matrix columns");
            }

            double[] result = new double[rows];

            for (int row = 0; row < ((MatrixUtil) object).getArray().length; row++) {
                double sum = 0;
                for(int column = 0; column < columns; column++){
                    double value = ((MatrixUtil) object).getArray()[row][column];
                    sum += value
                            * this.array[row];
                }
                result[row] = sum;
            }
            this.array = result;
        }
        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable sum(Calculable object, DataHandler dataHandler) {
        int largestVectorSize = this.array.length > ((VectorUtil)object).array.length? this.array.length : ((VectorUtil)object).array.length;
        double[] firstVectorValues = new double[largestVectorSize];
        double[] secondVectorValues = new double[largestVectorSize];

        System.arraycopy(this.array, 0, firstVectorValues, 0, this.array.length);
        System.arraycopy(((VectorUtil)object).array, 0, secondVectorValues, 0, ((VectorUtil)object).array.length);

        for(int i = 0; i < largestVectorSize; i++){
            firstVectorValues[i] += secondVectorValues[i];
        }

        dataHandler.writeCalculationObjects(this, object, '+');

        this.array = firstVectorValues;

        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable subtract(Calculable object, DataHandler dataHandler) {
        int largestVectorSize = Math.max(this.array.length, ((VectorUtil) object).array.length);
        double[] firstVectorValues = new double[largestVectorSize];
        double[] secondVectorValues = new double[largestVectorSize];

        System.arraycopy(this.array, 0, firstVectorValues, 0, this.array.length);
        System.arraycopy(((VectorUtil)object).array, 0, secondVectorValues, 0, ((VectorUtil)object).array.length);

        for(int i = 0; i < largestVectorSize; i++){
            firstVectorValues[i] -= secondVectorValues[i];
        }

        dataHandler.writeCalculationObjects(this, object, '-');

        this.array = firstVectorValues;

        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public String toString() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", decimalFormatSymbols);
        StringBuilder vectorString = new StringBuilder();
        vectorString.append("[");
        for(int i = 0; i < this.array.length; i++){
            vectorString.append(df.format(this.array[i]));
            if(i != this.array.length-1) {
                vectorString.append(" ");
            }
        }
        vectorString.append("]");
        return vectorString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorUtil vectorUtil = (VectorUtil) o;
        return Arrays.equals(this.array, vectorUtil.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}