import java.util.Arrays;

public class Matrix {
    private int columns;
    private int rows;
    private int array[][];

    public Matrix(int[][] array) {
        this.array = array;
    }

    public Matrix(int columns, int rows){
        this.array = new int[columns][rows];
        this.columns = columns;
        this. rows = rows;
    }

    public int[][] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.deepEquals(array, matrix.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
