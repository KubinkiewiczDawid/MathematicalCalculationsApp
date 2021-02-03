import java.util.Scanner;

public class NumberUtil implements Calculable{
    private double value;

    public NumberUtil() {}

    public NumberUtil(double value){
        this.value = value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double addNumbers(double x, double y){
        return x + y;
    }

    @Override
    public void makeCalculation(Object object) {
        if(object instanceof NumberUtil){
            handleTwoNumbersCase((NumberUtil) object);
        }
    }

    private void handleTwoNumbersCase(NumberUtil object) {
        boolean shouldContinue = false;

        do{
            System.out.println("1. Summary");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exponentiation");
            System.out.println("6. Square root");
            System.out.println("7. Leave");

            switch (Main.getUserNumericInput()){
                case 1 -> summ(this.value, object.getValue());
                case 2 -> subtract(this.value, object.getValue());
                case 3 -> multiply(this.value, object.getValue());
                case 4 -> {
                    try {
                        divide(this.value, object.getValue());
                    }catch (ArithmeticException e){
                        System.out.print(e.getMessage());
                        System.out.println(", try again");
                        shouldContinue = true;
                    }
                }
                case 5 -> pow(this.value, object.getValue());
                case 6 -> sqrRoot(this.value, object.getValue());
                case 7 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
    }

    private void summ(double x, double y) {
        System.out.println(x + y);
    }

    private void subtract(double x, double y) {
        System.out.println(x - y);
    }

    private void multiply(double x, double y) {
        System.out.println(x * y);
    }

    private void divide(double x, double y)throws ArithmeticException{
        if(y != 0) {
            System.out.println(x / y);
        }else{
            throw new ArithmeticException("You can't divide by 0");
        }
    }

    private void pow(double x, double y) {
        if(y < 0 || y > 128) {
            System.out.println("Size of exponent is not supported,");
            do {
                System.out.println("please enter a number between 0 and 128");
                y = Main.getUserNumericInput();
            } while (y >= 0 && y <= 128);
        }
        System.out.println(Math.pow(x, y));
    }

    private void sqrRoot(double x, double y) {
        System.out.printf("square root of %.2f is %.2f\n", x, Math.sqrt(x));
        System.out.printf("square root of %.2f is %.2f", y, Math.sqrt(y));
    }
}
