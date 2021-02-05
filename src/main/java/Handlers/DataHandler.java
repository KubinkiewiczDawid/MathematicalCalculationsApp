package Handlers;

import Utils.Calculable;

import java.io.*;

public class DataHandler {
    private final static String FILE_NAME = "historia_obliczen.txt";
    private final static int MAX_AMOUNT_OF_DATA = 50;
    private File logFile;

    public DataHandler() {
    }

    public void createFile(){
        this.logFile = new File(this.FILE_NAME);
        try {
            this.logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCopyFile(){
        StringBuilder fileNameBuilder = new StringBuilder();
        File newLogFile = new File(this.FILE_NAME + ".1");
        fileNameBuilder.append(newLogFile.getName());
        if (newLogFile.exists()) {
            while(newLogFile.exists()){
                String fileNumberText = newLogFile.getName().replace(this.FILE_NAME, "");
                fileNumberText = fileNumberText.replace(".", "");
                int indexOfNumber = fileNameBuilder.indexOf(fileNumberText);
                fileNameBuilder.delete(indexOfNumber, fileNameBuilder.length());
                fileNameBuilder.append(Integer.parseInt(fileNumberText) + 1);

                newLogFile = new File(fileNameBuilder.toString());
            }
        }
        try {
            this.logFile.renameTo(newLogFile);
            this.logFile.createNewFile();
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String s){
        try {
            PrintWriter out = new PrintWriter(new FileWriter(logFile, true));
            out.append(s);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeCalculationObjects(Calculable calculable, Calculable calculable2, char operation){
        if(getAmountOfDataWritten() >= MAX_AMOUNT_OF_DATA){
            createCopyFile();
        }
        String s = calculable.toString() + "\n" + operation + "\n" + calculable2.toString();
        System.out.print(s);
        writeData(s);
    }

    public void writeCalculationResult(Calculable calculable){
        String s = "\n=\n" + calculable.toString() + "\n" + "____________________________" + "\n";
        System.out.print(s);
        writeData(s);
    }

    private int getAmountOfDataWritten(){
        int numberOfData = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
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