import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C:\\Users\\panchuk\\IdeaProjects\\hangman\\src\\words.txt"));
        Scanner keyboard = new Scanner(System.in);
        List<String> words = new ArrayList<>();



        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));
        System.out.println(word);
        List<Character> playerGuesses = new ArrayList<>();
        while (true) {
            playerPrompt(keyboard, playerGuesses, word);
            System.out.println("Try to guess whole word: ");
            if (keyboard.nextLine().equals(word)) {
                System.out.println("You win!");
                System.exit(0);
            }
            else {
                System.out.println("Try again!");
            }
        }


    }

    private static void playerPrompt(Scanner keyboard, List<Character> playerGuesses, String word) {
        System.out.println("Please enter a letter: ");
        String letterGuess = keyboard.nextLine();
        playerGuesses.add(letterGuess.charAt(0));
        printLettersGuessed(word, playerGuesses);
    }

    private static void printLettersGuessed(String word, List<Character> playerGuesses) {
        int correctGuesses = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctGuesses++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
        if (correctGuesses == word.length()) {
            System.out.println("You win!");
            System.exit(0);
        }
    }
}