import static org.junit.jupiter.api.Assertions.assertEquals;

import Exceptions.IncorrectDataLength;
import Handlers.DataHandler;
import Utils.MatrixUtil;
import Utils.NumberUtil;
import Utils.VectorUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalculatorTests {

    Object calculator;
    Method getMatrixFromString;
    Method getVectorFromString;
    Method getNumberFromString;
    {
        try {
            calculator = Calculator.class.newInstance();
            getMatrixFromString = calculator.getClass().getDeclaredMethod("getMatrixFromString", String.class);
            getMatrixFromString.setAccessible(true);
            getVectorFromString = calculator.getClass().getDeclaredMethod("getVectorFromString", String.class);
            getVectorFromString.setAccessible(true);
            getNumberFromString = calculator.getClass().getDeclaredMethod("isNumeric", String.class);
            getNumberFromString.setAccessible(true);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void matrixFromStringTest(){
        double[][] matrixData = {{1,2}, {1,1}};
        MatrixUtil matrixUtil = new MatrixUtil(matrixData,null);
        MatrixUtil matrixUtilFromString = null;
        try {
            matrixUtilFromString = (MatrixUtil) getMatrixFromString.invoke(calculator, "[1,2/1,1]");//getMatrixFromString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        assertEquals(matrixUtil, matrixUtilFromString);
    }

    @Test
    public void vectorFromStringTest(){
        double[] vectorData = {1,2.1,0,5.1};
        VectorUtil vectorUtil = new VectorUtil(vectorData, null);
        VectorUtil vectorUtilFromString = null;
        try {
            vectorUtilFromString = (VectorUtil) getVectorFromString.invoke(calculator, "[1,2.1,,5.1]");//getMatrixFromString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        assertEquals(vectorUtil, vectorUtilFromString);
    }

    @Test
    public void numberFromStringTest(){
        double numberData = 5.21;
        double stringValue = 0;
        NumberUtil numberUtil = new NumberUtil(numberData);
        NumberUtil numberUtilFromString = null;
        try {
            stringValue = (double) getNumberFromString.invoke(calculator, "5.210");//getMatrixFromString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        numberUtilFromString = new NumberUtil(stringValue);

        assertEquals(numberUtil, numberUtilFromString);
    }

    @Test
    public void multiplyMatrices(){
        try {
            MatrixUtil matrix1 = (MatrixUtil) getMatrixFromString.invoke(calculator, "[1,2/1,2]");
            MatrixUtil matrix2 = (MatrixUtil) getMatrixFromString.invoke(calculator, "[1,2/1,2]");

            MatrixUtil correctResult = (MatrixUtil) getMatrixFromString.invoke(calculator, "[3,6/3,6]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(matrix1.multiply(matrix2, mockedDataHandler), correctResult);
        } catch (IncorrectDataLength | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addMatrices(){
        try {
            MatrixUtil matrix1 = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/,1,2/,1,2]");
            MatrixUtil matrix2 = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,,/6,1,1/,9,2]");

            MatrixUtil correctResult = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/6,2,3/,10,4]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(matrix1.sum(matrix2, mockedDataHandler), correctResult);
        } catch (IncorrectDataLength | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyMatrixWithNumber(){
        try {
            MatrixUtil matrix = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/,1,2/,1,2]");
            NumberUtil number = new NumberUtil(5.1);

            MatrixUtil correctResult = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,5.1,/,5.1,10.2/,5.1,10.2]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(matrix.multiply(number, mockedDataHandler), correctResult);
        } catch (IllegalAccessException | InvocationTargetException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyNumberWithMatrix(){
        try {
            MatrixUtil matrix = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/,1,2/,1,2]");
            NumberUtil number = new NumberUtil(5.1);

            MatrixUtil correctResult = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,5.1,/,5.1,10.2/,5.1,10.2]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(number.multiply(matrix, mockedDataHandler), correctResult);
        } catch (IllegalAccessException | InvocationTargetException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyMatrixWithVector(){
        try {
            MatrixUtil matrix = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/,1,2/,1,2]");
            VectorUtil vector = (VectorUtil) getVectorFromString.invoke(calculator, "[2.6,1,]");

            VectorUtil correctResult = (VectorUtil) getVectorFromString.invoke(calculator, "[2.6,3,]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(matrix.multiply(vector, mockedDataHandler), correctResult);
        } catch (IllegalAccessException | InvocationTargetException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyVectorWithMatrix(){
        try {
            MatrixUtil matrix = (MatrixUtil) getMatrixFromString.invoke(calculator, "[,1,/,1,2/,1,2]");
            VectorUtil vector = (VectorUtil) getVectorFromString.invoke(calculator, "[2.6,1,]");

            VectorUtil correctResult = (VectorUtil) getVectorFromString.invoke(calculator, "[2.6,3,]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(vector.multiply(matrix, mockedDataHandler), correctResult);
        } catch (IllegalAccessException | InvocationTargetException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiplyVectorWithNumber(){
        try {
            VectorUtil vector = (VectorUtil) getVectorFromString.invoke(calculator, "[2.6,1,]");
            NumberUtil number = new NumberUtil(5.1);

            VectorUtil correctResult = (VectorUtil) getVectorFromString.invoke(calculator, "[13.26,5.1,]");

            DataHandler mockedDataHandler = Mockito.mock(DataHandler.class);

            assertEquals(vector.multiply(number, mockedDataHandler), correctResult);
        } catch (IllegalAccessException | InvocationTargetException | IncorrectDataLength e) {
            e.printStackTrace();
        }
    }
}