import static org.junit.jupiter.api.Assertions.assertEquals;

import Exceptions.IncorrectDataLength;
import Exceptions.TooBigMatrixException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

        assertEquals(matrixUtil, matrixUtilFromString);
    }

    @Test
    public void multiplyMatrices(){
        try {
            MatrixUtil matrix1 = Main.getMatrixFromString("[1,2/1,1]");
            MatrixUtil matrix2 = Main.getMatrixFromString("[1,2/1,1]");

            MatrixUtil correctResult = Main.getMatrixFromString("[3,6/3,6]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(matrix1.multiply(matrix2, mockedDataHandler), correctResult);
        } catch (TooBigMatrixException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }
}
