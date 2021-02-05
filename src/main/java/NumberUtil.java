import Exceptions.IncorrectDataLength;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

public class NumberUtil implements Calculable{
    private double value;
    private final DataHandler dataHandler;

    public NumberUtil(double value){
        this.value = value;
        this.dataHandler = Main.dataHandler;
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
    public Calculable multiply(Calculable object, DataHandler dataHandler) throws IncorrectDataLength {
        if(object instanceof MatrixUtil | object instanceof VectorUtil){
            return object.multiply(this, dataHandler);
        }
        return numbersMultiply((NumberUtil) object, dataHandler);
    }

    private NumberUtil numbersMultiply(NumberUtil object, DataHandler dataHandler) {
        dataHandler.writeCalculationObjects(this, object, '*');
        this.value *= object.getValue();
        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable sum(Calculable object, DataHandler dataHandler) {
        dataHandler.writeCalculationObjects(this, object, '+');
        this.value += ((NumberUtil) object).getValue();
        dataHandler.writeCalculationResult(this);
        return this;
    }

    @Override
    public Calculable subtract(Calculable object, DataHandler dataHandler) {
        dataHandler.writeCalculationObjects(this, object, '-');
        this.value -= ((NumberUtil) object).getValue();
        dataHandler.writeCalculationResult(this);
        return this;
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

            switch (Main.getUserNumericInput()){
                case 1 -> this.sum(object, this.dataHandler);
                case 2 -> this.subtract(object, this.dataHandler);
                case 3 -> this.multiply(object, this.dataHandler);
                case 4 -> {
                    try {
                        divide((NumberUtil)object, this.dataHandler);
                    }catch (ArithmeticException e){
                        System.out.print(e.getMessage());
                        System.out.println(", try again");
                        shouldContinue = true;
                    }
                }
                case 5 -> pow((NumberUtil)object, this.dataHandler);
                case 6 -> sqrRoot(this.dataHandler);
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

            switch (Main.getUserNumericInput()){
                case 1 -> object.multiply(this, this.dataHandler);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private Calculable divide(NumberUtil object, DataHandler dataHandler)throws ArithmeticException{
        if(object.value != 0) {
            dataHandler.writeCalculationObjects(this, object, '/');
            this.value = this.value / object.value;
            dataHandler.writeCalculationResult(this);
            return this;
        }else{
            throw new ArithmeticException("You can't divide by 0");
        }
    }

    private Calculable pow(NumberUtil object, DataHandler dataHandler) {
        if(object.value < 0 || object.value > 128) {
            System.out.println("Size of exponent is not supported,");
            do {
                System.out.println("please enter a number between 0 and 128");
                object.value = Main.getUserNumericInput();
            } while (object.value >= 0 && object.value <= 128);
        }
        dataHandler.writeCalculationObjects(this, object, '^');
        this.value = Math.pow(this.value, object.value);
        dataHandler.writeCalculationResult(this);
        return this;
    }

    private Calculable sqrRoot(DataHandler dataHandler) {
        dataHandler.writeCalculationObjects(this, new NumberUtil(2), '√');
        this.value = Math.sqrt(this.value);
        dataHandler.writeCalculationResult(this);
        return this;
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