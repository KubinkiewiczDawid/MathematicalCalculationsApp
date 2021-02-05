import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataExporter {
    private String fileName;
    private File logFile;

    public DataExporter(String fileName) {
        this.fileName = fileName;
    }

    public void createFile(){
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.fileName);
        this.logFile = new File(fileNameBuilder.toString());
        while(this.logFile.exists()){
            String fileNumberText = this.logFile.getName().replace(this.fileName, "");
            if(fileNumberText.equals("")){
                fileNameBuilder.append(".1");
            }else{
                fileNumberText = fileNumberText.replace(".", "");
                fileNameBuilder.deleteCharAt(fileNameBuilder.indexOf(fileNumberText));
                fileNameBuilder.deleteCharAt(fileNameBuilder.length()-1);
                fileNameBuilder.append(".").append(Integer.parseInt(fileNumberText) + 1);
            }
            this.logFile = new File(fileNameBuilder.toString());
        }
        try {
            this.logFile.createNewFile();
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
}
