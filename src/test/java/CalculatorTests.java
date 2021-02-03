import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTests {

    @Test
    public void matrixToStringTest(){
        int[][] matrixData = {{1,2}, {1,1}};
        MatrixUtil matrixUtil = new MatrixUtil(matrixData);
        MatrixUtil matrixUtilFromString = Main.getMatrixFromString("[1,2/1,1]");

        System.out.println(matrixUtil);
        System.out.println(matrixUtilFromString);

        assertEquals(matrixUtil, matrixUtilFromString);
    }

}