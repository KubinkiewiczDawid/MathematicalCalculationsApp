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
            boolean shouldContinue = false;

            do{
                System.out.println("1. Summary");
                System.out.println("2. Subtraction");
                System.out.println("3. Multiplication");
                System.out.println("4. Division");
                System.out.println("5. Exponentiation");
                System.out.println("6. Square root");

                switch (Main.getUserNumericInput()){
                    case 1 -> summ(this.value, ((NumberUtil) object).getValue());
//                    case 2 -> subtract(this.value, ((NumberUtil) object).getValue());
//                    case 3 -> multiply(this.value, ((NumberUtil) object).getValue());
//                    case 4 -> divide(this.value,((NumberUtil) object).getValue());
//                    case 5 -> pow(this.value, ((NumberUtil) object).getValue());
//                    case 6 -> sqrRoot(this.value, ((NumberUtil) object).getValue());
                    default -> {
                        System.out.println("Invalid option, try again");
                        shouldContinue = true;
                    }
                }
            }while(shouldContinue);
        }
    }

    private void summ(double x, double y) {
        System.out.println(x + y);
    }
}
