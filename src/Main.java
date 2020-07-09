import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Model.getWordsFromDictionary();
        try (Scanner keyboard = new Scanner(System.in)) {
            System.out.print("Please input your Scrabble Letters(enter \"quit\" to exit): ");
            while (keyboard.hasNextLine()) {
                String input = keyboard.next();
                if (input.equalsIgnoreCase("quit")) {
                    break;
                }
                if(checkIfInputIsValid(input)) {
                    Controller.unscrabbleTheLetters(input);
                    Controller.clearAllLists();
                    System.out.print("Please input new Scrabble Letters(enter \"quit\" to exit): ");
                }else
                    System.out.println("Please only type in letters...");
            }
        }
    }
    private static boolean checkIfInputIsValid(String input){
        for (int i = 0; i < input.length(); i++) {
            if ((!Character.isLetter(input.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}





