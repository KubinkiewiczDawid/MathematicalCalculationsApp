import Exceptions.IncorrectInputDataException;
import Exceptions.InsufficientAmountOfDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        List<Object> inputDataToHandle = null;

        System.out.println("Write two input data separated with space:");
        do {
            try {
                inputDataToHandle = getInputData();
            } catch (InsufficientAmountOfDataException | IncorrectInputDataException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again");
            }
        }while(inputDataToHandle==null);

        handleData(inputDataToHandle);
    }

    private static void handleData(List<Object> inputDataToHandle) {
        if(inputDataToHandle.get(0) instanceof NumberUtil ||
                inputDataToHandle.get(0) instanceof MatrixUtil ||
                inputDataToHandle.get(0) instanceof VectorUtil) {
            System.out.println("doubles");
           ((Calculable) inputDataToHandle.get(0)).makeCalculation((Calculable) inputDataToHandle.get(1));
        }
    }

    public static MatrixUtil getMatrixFromString(String s) {
        s = removeSquareBrackets(s);
        System.out.println(s);
        String[] rows = s.split("/", -1);

       int longestColumnSize = 0;
       for(String row : rows){
           String[] columns = row.split(",", -1);
           int columnsSize = 0;
           for(String x : columns){
               columnsSize++;
           }
           if(columnsSize > longestColumnSize) longestColumnSize = columnsSize;
       }

        System.out.println(rows.length + " - " + longestColumnSize);

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
                    matrixData[i][j] = Integer.parseInt(columns[j]);
                }
            }
        }
        return new MatrixUtil(matrixData);
    }

    private static VectorUtil getVectorFromString(String s) {
        s = removeSquareBrackets(s);
        String[] values = s.split(",", -1);

        int[] vectorData = new int[values.length];
        Arrays.fill(vectorData, 0);

        for(int i = 0; i < values.length; i++){
            if(values[i].equals("")){
                vectorData[i] = 0;
            }else{
                vectorData[i] = Integer.parseInt(values[i]);
            }
        }

        return new VectorUtil(vectorData);
    }

    private static String removeSquareBrackets(String s) {
        String pattern = "[\\x5B\\x5D]";
        s = s.replaceAll(pattern, "");
        return s;
    }

    public static void print2dArray(int[][] arrays){
        for(int[] array: arrays){
            for(int x : array){
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    private static List<Object> getInputData() throws InsufficientAmountOfDataException, IncorrectInputDataException {
        String inputData = scanner.nextLine();

        return validateInputData(inputData);
    }

    private static List<Object> validateInputData(String inputData) throws InsufficientAmountOfDataException, IncorrectInputDataException {

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

    private static void checkSingleData(List<Object> inputDataToHandle, String singleInputData) throws IncorrectInputDataException {
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

    private static Double isNumeric(String data){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(pattern.matcher(data).matches()){
            return Double.parseDouble(data);
        }
        return null;
    }

    private static MatrixUtil isMatrix(String data){
        if(data.length() <= 3 || data.charAt(0) != '[' || data.charAt(data.length()-1) != ']' || !data.contains("/")) return null;

        for(int i = 1; i < data.length()-1; i++){
            if(data.charAt(i) != ',' && data.charAt(i) != '/' && isNumeric(Character.toString(data.charAt(i))) == null){
                return null;
            }
        }
        return getMatrixFromString(data);
    }

    private static VectorUtil isVector(String data){
        if(data.length() <= 2 || data.charAt(0) != '[' || data.charAt(data.length()-1) != ']') return null;

        for(int i = 1; i < data.length()-1; i++){
            if(data.charAt(i) != ',' && isNumeric(Character.toString(data.charAt(i))) == null){
                return null;
            }
        }
        return getVectorFromString(data);
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