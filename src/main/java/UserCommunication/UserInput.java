package UserCommunication;

import java.util.Scanner;

public class UserInput {

    Scanner scanner;

    public UserInput(){
        this.scanner = new Scanner(System.in);
    }

    public String getUserDataInput(){
        return scanner.nextLine();
    }

    public int getUserNumericInput(){
        while(true) {
            try {
                return Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException  e) {
                System.out.println("Sprobuj jeszcze raz");
            }
        }
    }
}
