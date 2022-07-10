import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("/home/barbossa/wordsHangman"));
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();

        ArrayList<String> wordsArrayEasy = new ArrayList<String>();
        ArrayList<String> wordsArrayMedium = new ArrayList<String>();
        ArrayList<String> wordsArrayHard = new ArrayList<String>();

        String wordToGuess = "";

        while (scanner.hasNext()) {
            String scannedWord = scanner.nextLine();
            if (scannedWord.length() <= 4) {
                wordsArrayEasy.add(scannedWord);
            }
            if (scannedWord.length() >= 5 && scannedWord.length() <= 9) {
                wordsArrayMedium.add(scannedWord);
            }
            if (scannedWord.length() >= 10) {
                wordsArrayHard.add(scannedWord);
            }
        }

        System.out.println("Choose difficulty level: Easy, Medium or Hard.");

        String difficultyChoise = keyboard.nextLine();

        if (difficultyChoise.equalsIgnoreCase("Easy")) {
            wordToGuess = wordsArrayEasy.get(random.nextInt(wordsArrayEasy.size()));
        }
        if (difficultyChoise.equalsIgnoreCase("Medium")) {
            wordToGuess = wordsArrayMedium.get(random.nextInt(wordsArrayMedium.size()));
        }
        if (difficultyChoise.equalsIgnoreCase("Hard")) {
            wordToGuess = wordsArrayHard.get(random.nextInt(wordsArrayHard.size()));
        }

        ArrayList<Character> playerGuesses = new ArrayList<Character>();

        int wrongCount = 0;

        System.out.println(wordToGuess);


        while(true) {

            printHangedMan(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The word was: " + wordToGuess);
                break;
            }

            printCurrentWordState(wordToGuess, playerGuesses);
            if (!getPlayerGuess(keyboard, wordToGuess, playerGuesses)) {
                wrongCount++;
            }

            if(printCurrentWordState(wordToGuess, playerGuesses)) {
                System.out.println("You win!");
                break;
            }

            System.out.println("Please enter your guess for the word:");
            if(keyboard.nextLine().equals(wordToGuess)) {
                System.out.println("You win!");
                break;
            }
            else {
                System.out.println("Nope! Try again!");
            }
        }


    }

    private static void printHangedMan(int wrongCount) {
        System.out.println(" -------");
        System.out.println(" |     |");

        if (wrongCount >= 1) {
            System.out.println(" O");
        }

        if (wrongCount >= 2) {
            System.out.print("\\ ");
            if (wrongCount >= 3) {
                System.out.print("/");
            }
            else {
                System.out.println("");
            }
        }

        if (wrongCount >= 4) {
            System.out.println("");
            System.out.println(" |");
        }

        if (wrongCount >= 5) {
            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.print("\\");
            }
            else {
                System.out.println("");
            }
        }
        System.out.println("");
    }

    private static boolean getPlayerGuess(Scanner keyboard, String wordToGuess, ArrayList<Character> playerGuesses) {
        System.out.println("Please enter a letter:");
        String letterGuess = keyboard.nextLine();
        playerGuesses.add(letterGuess.charAt(0));

        return wordToGuess.contains(letterGuess);
    }

    private static boolean printCurrentWordState(String wordToGuess, ArrayList<Character> playerGuesses) {
        int correctCount = 0;
        for(int i = 0; i < wordToGuess.length(); i++) {
            if(playerGuesses.contains(wordToGuess.charAt(i))) {
                System.out.print(wordToGuess.charAt(i));
                correctCount++;
            }
            else {
                System.out.print("-");
            }
        }
        System.out.println();

        return(wordToGuess.length() == correctCount);
    }
}