package UserCommunication;

import java.util.Scanner;

public class UserInput {

    private static Scanner scanner = new Scanner(System.in);

    public static String getUserDataInput(){
        return scanner.nextLine();
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