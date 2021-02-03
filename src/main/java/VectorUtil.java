import java.util.Arrays;

public class VectorUtil implements Calculable{
    private int array[];

    public VectorUtil(int[] array) {
        this.array = array;
    }

    public VectorUtil() {}


    public int[] getArray() {
        return array;
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
    public void makeCalculation(Object object) {
        System.out.println("vector calculation");
    }
}
