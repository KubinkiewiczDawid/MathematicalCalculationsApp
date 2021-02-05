package Utils;

import Exceptions.IncorrectDataLength;
import Handlers.DataHandler;
import UserCommunication.UserInput;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

public class NumberUtil implements Calculable {
    private double value;

    public NumberUtil(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void makeCalculation(Calculable object) throws IncorrectDataLength {
        if(object instanceof NumberUtil){
            handleTwoNumbersCase(object);
        }else{
            handleMatrixVectorCase(object);
        }
    }

    @Override
    public Calculable multiply(Calculable object) throws IncorrectDataLength {
        if(object instanceof MatrixUtil | object instanceof VectorUtil){
            return object.multiply(this);
        }
        return numbersMultiply((NumberUtil) object);
    }

    private NumberUtil numbersMultiply(NumberUtil object) {
        return new NumberUtil(this.value * object.getValue());
    }

    @Override
    public Calculable sum(Calculable object) {
        return new NumberUtil(this.value + ((NumberUtil) object).getValue());
    }

    @Override
    public Calculable subtract(Calculable object) {
        return new NumberUtil(this.value - ((NumberUtil) object).getValue());
    }

    private void handleTwoNumbersCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("1. Summary");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exponentiation");
            System.out.println("6. Square root");
            System.out.println();
            System.out.println("0. Leave");

            switch (UserInput.getUserNumericInput()){
                case 1 -> DataHandler.writeCalculation(this, object, this.sum(object), '+');
                case 2 -> DataHandler.writeCalculation(this, object, this.subtract(object), '-');
                case 3 -> DataHandler.writeCalculation(this, object, this.multiply(object), '*');
                case 4 -> {
                    try {
                        DataHandler.writeCalculation(this, object, divide((NumberUtil)object), '/');
                    }catch (ArithmeticException e){
                        System.out.print(e.getMessage());
                        System.out.println(", try again");
                        shouldContinue = true;
                    }
                }
                case 5 -> DataHandler.writeCalculation(this, object, pow((NumberUtil)object), '^');
                case 6 -> DataHandler.writeCalculation(this, object, sqrRoot((NumberUtil)object), '√');
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private void handleMatrixVectorCase(Calculable object) throws IncorrectDataLength {
        boolean shouldContinue = false;

        do{
            System.out.println("Multiplication is the only operation allowed here");
            System.out.println("1. Multiplication");
            System.out.println();
            System.out.println("0. Leave");

            switch (UserInput.getUserNumericInput()){
                case 1 -> DataHandler.writeCalculation(object, this, object.multiply(this), '*');
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private Calculable divide(NumberUtil object)throws ArithmeticException{
        if(object.value != 0) {
            return new NumberUtil(this.value / object.value);
        }else{
            throw new ArithmeticException("You can't divide by 0");
        }
    }

    private Calculable pow(NumberUtil object) {
        if(object.value < 0 || object.value > 128) {
            System.out.println("Size of exponent is not supported,");
            do {
                System.out.println("please enter a number between 0 and 128");
                object.value = UserInput.getUserNumericInput();
            } while (object.value >= 0 && object.value <= 128);
        }
        return new NumberUtil(Math.pow(this.value, object.value));
    }

    private Calculable sqrRoot(NumberUtil object) {
        return new NumberUtil(Math.pow(this.value, 1.0 / object.value));
    }

    @Override
    public String toString() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", decimalFormatSymbols);
        return String.valueOf(df.format(this.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberUtil that = (NumberUtil) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}