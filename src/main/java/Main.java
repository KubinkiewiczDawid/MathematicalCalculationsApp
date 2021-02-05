import Exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DataHandler dataHandler = new DataHandler();
    public static void main(String[] args) {
        dataHandler.createFile();
        while(true) {
            List<Object> inputDataToHandle = null;
            System.out.println("Write two input data separated with space:");
            do {
                try {
                    inputDataToHandle = getInputData();
                } catch (InsufficientAmountOfDataException | IncorrectInputDataException | TooBigMatrixException | TooBigVectorExeption e) {
                    System.out.println(e.getMessage());
                    System.out.println("Try again");
                }
            } while (inputDataToHandle == null);

            handleData(inputDataToHandle);
        }
    }

    private static void handleData(List<Object> inputDataToHandle) {
        if(inputDataToHandle.get(0) instanceof NumberUtil ||
                inputDataToHandle.get(0) instanceof MatrixUtil ||
                inputDataToHandle.get(0) instanceof VectorUtil) {
            try {
                ((Calculable) inputDataToHandle.get(0)).makeCalculation((Calculable) inputDataToHandle.get(1));
            } catch (IncorrectDataLength e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String removeSquareBrackets(String s) {
        String pattern = "[\\x5B\\x5D]";
        s = s.replaceAll(pattern, "");
        return s;
    }

    private static List<Object> getInputData() throws InsufficientAmountOfDataException, IncorrectInputDataException, TooBigMatrixException, TooBigVectorExeption {
        String inputData = scanner.nextLine();

        return validateInputData(inputData);
    }

    private static List<Object> validateInputData(String inputData) throws InsufficientAmountOfDataException, IncorrectInputDataException, TooBigMatrixException, TooBigVectorExeption {

        String[] inputDataElements = inputData.split(" ");

        if(inputDataElements.length != 2){
            throw new InsufficientAmountOfDataException();
        }

        List<Object> inputDataToHandle = new ArrayList<>();

        for(String singleInputData : inputDataElements) {
            checkSingleData(inputDataToHandle, singleInputData);
        }

        return inputDataToHandle;
    }

    private static void checkSingleData(List<Object> inputDataToHandle, String singleInputData) throws IncorrectInputDataException, TooBigMatrixException, TooBigVectorExeption {
        Object object;
        if(isNumeric(singleInputData) != null){
            object = new NumberUtil(isNumeric(singleInputData));
        } else if(isMatrix(singleInputData) != null){
            object = isMatrix(singleInputData);
        } else if(isVector(singleInputData) != null){
            object = isVector(singleInputData);
        } else{
            throw new IncorrectInputDataException();
        }
        inputDataToHandle.add(object);
    }

    private static Double isNumeric(String s){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(pattern.matcher(s).matches()){
            return Double.parseDouble(s);
        }
        return null;
    }

    private static MatrixUtil isMatrix(String s) throws TooBigMatrixException {
        if(s.length() <= 3 || s.charAt(0) != '[' || s.charAt(s.length()-1) != ']' || !s.contains("/")) return null;
       // s = removeSquareBrackets(s);
        for(int i = 1; i < s.length()-1; i++){
            if(s.charAt(i) != ',' && s.charAt(i) != '/' && isNumeric(Character.toString(s.charAt(i))) == null && s.charAt(i) != '.'){
                return null;
            }
        }
        return getMatrixFromString(s);
    }

    private static MatrixUtil getMatrixFromString(String s) throws TooBigMatrixException {
        s = removeSquareBrackets(s);
        String[] rows = s.split("/", -1);
        if(rows.length > 4){
            throw new TooBigMatrixException();
        }

        int longestColumnSize = 0;
        for(String row : rows){
            String[] columns = row.split(",", -1);
            int columnsSize = 0;
            for(String x : columns){
                columnsSize++;
            }
            if(columnsSize > longestColumnSize) longestColumnSize = columnsSize;
        }

        if(longestColumnSize > 4){
            throw new TooBigMatrixException();
        }

        double[][] matrixData = new double[rows.length][longestColumnSize];
        for(double[] array : matrixData){
            Arrays.fill(array, 0);
        }

        for(int i = 0; i < rows.length; i++){
            String[] columns = rows[i].split(",", -1);
            for(int j = 0; j < columns.length; j++){
                if(columns[j].equals("")){
                    matrixData[i][j] = 0;
                }else{
                    matrixData[i][j] = Double.parseDouble(columns[j]);
                }
            }
        }
        return new MatrixUtil(matrixData);
    }

    private static VectorUtil isVector(String s) throws TooBigVectorExeption {
        if(s.length() <= 3 || s.charAt(0) != '[' || s.charAt(s.length()-1) != ']') return null;

        for(int i = 1; i < s.length()-1; i++){
            if(s.charAt(i) != ',' && isNumeric(Character.toString(s.charAt(i))) == null && s.charAt(i) != '.'){
                return null;
            }
        }
        return getVectorFromString(s);
    }

    private static VectorUtil getVectorFromString(String s) throws TooBigVectorExeption {
        s = removeSquareBrackets(s);
        String[] values = s.split(",", -1);

        if(values.length > 4){
            throw new TooBigVectorExeption();
        }

        double[] vectorData = new double[values.length];
        Arrays.fill(vectorData, 0);

        for(int i = 0; i < values.length; i++){
            if(values[i].equals("")){
                vectorData[i] = 0;
            }else{
                vectorData[i] = Double.parseDouble(values[i]);
            }
        }

        return new VectorUtil(vectorData);
    }

    public static int getUserNumericInput(){
        while(true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException  e) {
                System.out.println("Sprobuj jeszcze raz");
            }
        }
    }
}