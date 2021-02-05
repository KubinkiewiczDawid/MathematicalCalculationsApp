import Exceptions.IncorrectDataLength;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class VectorUtil implements Calculable{
    private double[] array;

    public VectorUtil(double[] array) {
        this.array = array;
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

            switch (Main.getUserNumericInput()){
                case 1 -> this.sum(object);
                case 2 -> this.subtract(object);
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

    @Override
    public void multiply(Calculable object) throws IncorrectDataLength {
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
                for (int column = 0; column < columns; column++) {
                    sum += ((MatrixUtil) object).getArray()[row][column]
                            * this.array[column];
                }
                result[row] = sum;
            }
            this.array = result;
        }
        System.out.println(this);
    }

    @Override
    public void sum(Calculable object) {
        int largestVectorSize = this.array.length > ((VectorUtil)object).array.length? this.array.length : ((VectorUtil)object).array.length;
        double[] firstVectorValues = new double[largestVectorSize];
        int[] secondVectorValues = new int[largestVectorSize];

        System.arraycopy(this.array, 0, firstVectorValues, 0, this.array.length);
        System.arraycopy(((VectorUtil)object).array, 0, secondVectorValues, 0, ((VectorUtil)object).array.length);

        for(int i = 0; i < largestVectorSize; i++){
            firstVectorValues[i] += secondVectorValues[i];
        }

        this.array = firstVectorValues;

        System.out.print("Vectors summary equals ");
        System.out.println(this);
    }

    @Override
    public void subtract(Calculable object) {
        int largestVectorSize = this.array.length > ((VectorUtil)object).array.length? this.array.length : ((VectorUtil)object).array.length;
        double[] firstVectorValues = new double[largestVectorSize];
        int[] secondVectorValues = new int[largestVectorSize];

        System.arraycopy(this.array, 0, firstVectorValues, 0, this.array.length);
        System.arraycopy(((VectorUtil)object).array, 0, secondVectorValues, 0, ((VectorUtil)object).array.length);

        for(int i = 0; i < largestVectorSize; i++){
            firstVectorValues[i] -= secondVectorValues[i];
        }

        this.array = firstVectorValues;

        System.out.print("Vectors subtraction equals ");
        System.out.println(this);
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
}