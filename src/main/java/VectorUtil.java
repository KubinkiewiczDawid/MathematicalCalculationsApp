import java.util.Arrays;

public class VectorUtil implements Calculable{
    private final int[] array;

    public VectorUtil(int[] array) {
        this.array = array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorUtil vectorUtil = (VectorUtil) o;
        return Arrays.equals(array, vectorUtil.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        StringBuilder vectorString = new StringBuilder();
        for(int x : array){
            vectorString.append(x);
            vectorString.append(" ");
        }
        vectorString.append("\n");
        return vectorString.toString();
    }

    @Override
    public void makeCalculation(Calculable object) {
        if(object instanceof VectorUtil){
            handleTwoVectorsCase(object);
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

    @Override
    public void multiply(Calculable object) {
        if(object instanceof NumberUtil){
            for(int i = 0; i < array.length; i++){
                array[i] *= ((NumberUtil) object).getValue();
            }
        }
        System.out.println(this);
    }

    @Override
    public void sum(Calculable object) {
        if(object instanceof VectorUtil){
            sumVectors((VectorUtil)object);
        }else {

        }
        System.out.println();
    }

    @Override
    public void subtract(Calculable object) {

    }

    private void sumVectors(VectorUtil object) {
        int longestVectorSize = Math.max(this.array.length, object.array.length);
        for(int i = 0; i < longestVectorSize; i++){

        }
    }
}