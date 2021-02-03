import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTests {

    @Test
    public void matrixToStringTest(){
        int[][] matrixData = {{1,2}, {1,1}};
        Matrix matrix = new Matrix(matrixData);

        Main.print2dArray(matrixData);

        assertEquals(matrix, Main.getMatrixFromString("[1,2/1,1]"));
    }

}
