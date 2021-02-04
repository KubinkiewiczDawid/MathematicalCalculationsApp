import static org.junit.jupiter.api.Assertions.assertEquals;

import Exceptions.TooBigMatrixException;
import org.junit.jupiter.api.Test;

public class CalculatorTests {

    @Test
    public void matrixToStringTest(){
        double[][] matrixData = {{1,2}, {1,1}};
        MatrixUtil matrixUtil = new MatrixUtil(matrixData);
        MatrixUtil matrixUtilFromString = null;
        try {
            matrixUtilFromString = Main.getMatrixFromString("[1,2/1,1]");
        } catch (TooBigMatrixException e) {
            e.printStackTrace();
        }

        System.out.println(matrixUtil);
        System.out.println(matrixUtilFromString);

        assertEquals(matrixUtil, matrixUtilFromString);
    }

}
