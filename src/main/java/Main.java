import Exceptions.IncorrectInputDataException;
import Exceptions.InsufficientAmountOfDataException;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        List<String> inputDataToHandle = null;

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

    private static void handleData(List<String> inputDataToHandle) {
        String dataTypes = inputDataToHandle.get(0).split(" ")[0] + " " + inputDataToHandle.get(1).split(" ")[0];
        System.out.println(dataTypes);

        switch (dataTypes){
            case "numeric numeric" -> {
                System.out.println("numerics");
                double number = Double.parseDouble(inputDataToHandle.get(0).split(" ")[1]);
                double number2 = Double.parseDouble(inputDataToHandle.get(1).split(" ")[1]);
                //handleNumericData(number, number2);
            }
            case "numeric vector" -> {
                System.out.println("number and vector");
                double number = Double.parseDouble(inputDataToHandle.get(0).split(" ")[1]);
                VectorUtil vectorUtil = getVectorFromString(inputDataToHandle.get(1).split(" ")[1]);
                System.out.println("Number");
                System.out.println(number);
                System.out.println("Vector");
                System.out.println(vectorUtil);
                //handleNumericVectorData(number, vectorUtil);
            }
            case "matrix matrix" -> {
                MatrixUtil matrixUtil = getMatrixFromString(inputDataToHandle.get(0).split(" ")[1]);
                MatrixUtil matrixUtil2 = getMatrixFromString(inputDataToHandle.get(1).split(" ")[1]);
                System.out.println(matrixUtil);
                System.out.println(matrixUtil2);
                //handleMatrixData(matrixUtil, matrixUtil2);
            }
            case "matrix numeric" ->{
                MatrixUtil matrixUtil = getMatrixFromString(inputDataToHandle.get(0).split(" ")[1]);
                double number = Double.parseDouble(inputDataToHandle.get(1).split(" ")[1]);
                System.out.println("Matrix");
                System.out.println(matrixUtil);
                System.out.println("Number");
                System.out.println(number);
                //handleMatrixNumeric
            }
            case "matrix vector" -> {
                System.out.println("vector and matrix");
                MatrixUtil matrixUtil = getMatrixFromString(inputDataToHandle.get(0).split(" ")[1]);
                VectorUtil vectorUtil2 = getVectorFromString(inputDataToHandle.get(1).split(" ")[1]);
                System.out.println(matrixUtil);
                System.out.println(vectorUtil2);
            }
            case "vector vector" -> {
                VectorUtil vectorUtil = getVectorFromString(inputDataToHandle.get(0).split(" ")[1]);
                VectorUtil vectorUtil2 = getVectorFromString(inputDataToHandle.get(1).split(" ")[1]);
                System.out.println(vectorUtil);
                System.out.println(vectorUtil2);
            }
        }
    }

    public static MatrixUtil getMatrixFromString(String s) {
        String pattern = "[\\x5B\\x5D]";
        s = s.replaceAll(pattern, "");
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

        int[][] matrixData = new int[rows.length][longestColumnSize];
        for(int[] array : matrixData){
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
        String pattern = "[\\x5B\\x5D]";
        s = s.replaceAll(pattern, "");
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

    public static void print2dArray(int[][] arrays){
        for(int[] array: arrays){
            for(int x : array){
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    private static void handleMatrixAndNumberCase(List<String> inputDataToHandle){

    }

    private static List<String> getInputData() throws InsufficientAmountOfDataException, IncorrectInputDataException {
        String inputData = scanner.nextLine();

        return validateInputData(inputData);
    }

    private static List<String> validateInputData(String inputData) throws InsufficientAmountOfDataException, IncorrectInputDataException {

        String[] inputDataElements = inputData.split(" ");

        if(inputDataElements.length != 2){
            throw new InsufficientAmountOfDataException();
        }

        List<String> inputDataToHandle = new ArrayList<>();

        for(String singleInputData : inputDataElements) {
            checkSingleData(inputDataToHandle, singleInputData);
        }

        inputDataToHandle.sort(String::compareTo);

        return inputDataToHandle;
    }

    private static void checkSingleData(List<String> inputDataToHandle, String singleInputData) throws IncorrectInputDataException {
        String dataType = "";
        if(isNumeric(singleInputData)){
            dataType = "numeric";
        } else if(isMatrix(singleInputData)){
            dataType = "matrix";
        } else if(isVector(singleInputData)){
            dataType = "vector";
        } else{
            throw new IncorrectInputDataException();
        }
        inputDataToHandle.add(dataType + " " + singleInputData);
    }

    private static boolean isNumeric(String data){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(pattern.matcher(data).matches()){
            return true;
        }
        return false;
    }

    private static boolean isMatrix(String data){
        if(data.length() <= 3 || data.charAt(0) != '[' || data.charAt(data.length()-1) != ']' || !data.contains("/")) return false;

        for(int i = 1; i < data.length()-1; i++){
            if(data.charAt(i) != ',' && data.charAt(i) != '/' && !isNumeric(Character.toString(data.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    private static boolean isVector(String data){
        if(data.length() <= 3 || data.charAt(0) != '[' || data.charAt(data.length()-1) != ']') return false;

        for(int i = 1; i < data.length()-1; i++){
            if(data.charAt(i) != ',' && !isNumeric(Character.toString(data.charAt(i)))){
                return false;
            }
        }
        return true;
    }
}
