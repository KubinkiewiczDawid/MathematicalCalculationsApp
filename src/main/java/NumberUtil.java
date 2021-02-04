import Exceptions.IncorrectDataLength;

public class NumberUtil implements Calculable{
    private final double value;

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
    public void multiply(Calculable object) throws IncorrectDataLength {
        if(object instanceof NumberUtil) {
            System.out.println(this.getValue() * ((NumberUtil) object).getValue());
        }else if(object instanceof MatrixUtil){
            object.multiply(this);
        }
    }

    @Override
    public void sum(Calculable object) {
        System.out.println(this.getValue() + ((NumberUtil)object).getValue());
    }

    @Override
    public void subtract(Calculable object) {
        System.out.println(this.getValue() - ((NumberUtil)object).getValue());
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
                case 1 -> this.sum(object);
                case 2 -> this.subtract(object);
                case 3 -> this.multiply(object);
                case 4 -> {
                    try {
                        divide(this.value, ((NumberUtil)object).getValue());
                    }catch (ArithmeticException e){
                        System.out.print(e.getMessage());
                        System.out.println(", try again");
                        shouldContinue = true;
                    }
                }
                case 5 -> pow(this.value, ((NumberUtil)object).getValue());
                case 6 -> sqrRoot(this.value, ((NumberUtil)object).getValue());
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
                case 1 -> object.multiply(this);
                case 0 -> shouldContinue = false;
                default -> {
                    System.out.println("Invalid option, try again");
                    shouldContinue = true;
                }
            }
        }while(shouldContinue);
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