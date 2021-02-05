package Handlers;

import Utils.Calculable;

import java.io.*;

public class DataHandler {
    private static final String FILE_PATH = "C:\\CalculatorData\\"; //TODO: make changable by user
    private static final String FILE_NAME = "historia_obliczen.txt";
    private final static int MAX_AMOUNT_OF_DATA = 50;
    private static File logFile;

    public static void createFile(){
        logFile = new File(FILE_PATH, FILE_NAME);
        try {
            if(!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdir();
            }
            if(!logFile.exists()){
                logFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCopyFile(){
        StringBuilder fileNameBuilder = new StringBuilder();
        File newLogFile = new File(FILE_PATH, FILE_NAME + ".1");
        fileNameBuilder.append(newLogFile.getName());
        if (newLogFile.exists()) {
            while(newLogFile.exists()){
                String fileNumberText = newLogFile.getName().replace(FILE_NAME, "");
                fileNumberText = fileNumberText.replace(".", "");
                int indexOfNumber = fileNameBuilder.indexOf(fileNumberText);
                fileNameBuilder.delete(indexOfNumber, fileNameBuilder.length());
                fileNameBuilder.append(Integer.parseInt(fileNumberText) + 1);

                newLogFile = new File(fileNameBuilder.toString());
            }
        }
        try {
            logFile.renameTo(newLogFile);
            logFile.createNewFile();
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeData(String s){
        try {
            PrintWriter out = new PrintWriter(new FileWriter(logFile, true));
            out.append(s);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCalculation(Calculable value1, Calculable value2, Calculable result, char operation){
        if(getAmountOfDataWritten() >= MAX_AMOUNT_OF_DATA){
            createCopyFile();
        }
        System.out.println("Operation:");
        String s = value1.toString() + "\n" + operation + "\n" + value2.toString() + "\n=\n" + result.toString() + "\n" + "____________________________" + "\n";

        System.out.print(s);
        writeData(s);
    }

    private static int getAmountOfDataWritten(){
        int numberOfData = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            while (reader.readLine() != null){
                if(reader.readLine().equals("____________________________")){
                    numberOfData++;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfData;
    }
}