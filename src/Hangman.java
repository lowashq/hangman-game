import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Main class for the Hangman game
public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        // Scanner to read words from a file located at the specified path
        Scanner scanner = new Scanner(new File("C:\\Users\\panchuk\\IdeaProjects\\hangman\\src\\words.txt"));
        // Scanner to read user input from the keyboard
        Scanner keyboard = new Scanner(System.in);

        // List to store all the words read from the file
        List<String> words = new ArrayList<>();

        // Loop through the file, adding each word to the list
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }

        // Randomly select a word from the list
        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));
        System.out.println(word); // For debugging purposes: Print the chosen word (remove in production)

        // Initialize the game state with the chosen word
        GameState gameState = new GameState(word);

        // Main game loop
        while (true) {
            // Display the hangman figure based on the number of wrong guesses
            printHangMan(gameState.getWrongGuesses());

            // Check if the player has exceeded the allowed number of wrong guesses
            if (gameState.getWrongGuesses() >= 3) {
                System.out.println("You lose!"); // Inform the player they lost
                break; // Exit the game loop
            }

            // Prompt the player to guess a letter
            if (!playerPrompt(keyboard, gameState)) {
                // If the guessed letter is incorrect, increment the wrong guesses counter
                gameState.incrementWrongGuesses();
            }

            // Display the word with correctly guessed letters revealed
            // If all letters are guessed correctly, the player wins
            if (printLettersGuessed(gameState)) {
                System.out.println("You win!"); // Inform the player they won
                break; // Exit the game loop
            }
        }
    }

    // Method to display the hangman figure based on the number of incorrect guesses
    private static void printHangMan(int wrongGuesses) {
        System.out.println("|----"); // Base structure of the hangman figure

        // Display the head if there's at least one wrong guess
        if (wrongGuesses >= 1) {
            System.out.println("|   0"); // Head
        }

        // Display the body and arms if there are at least two wrong guesses
        if (wrongGuesses >= 2) {
            System.out.println("|  /|\\"); // Body with arms
        }

        // Display the legs if there are three wrong guesses
        if (wrongGuesses >= 3) {
            System.out.println("|  / \\"); // Legs
        }
    }

    // Method to prompt the player to guess a letter
    private static boolean playerPrompt(Scanner keyboard, GameState gameState) {
        System.out.println("Please enter a letter: "); // Prompt message for the player
        String letterGuess = keyboard.nextLine(); // Read the player's input
        char guessedLetter = letterGuess.charAt(0); // Get the first character of the input

        // Add the guessed letter to the set of guessed letters
        gameState.addGuessedLetter(guessedLetter);

        // Check if the guessed letter is not part of the word
        if (!gameState.getWord().contains(letterGuess)) {
            System.out.println("Incorrect guess"); // Inform the player of a wrong guess
            return false; // Return false indicating the guess was incorrect
        }

        return true; // Return true indicating the guess was correct
    }

    // Method to display the word with correctly guessed letters and dashes for remaining letters
    private static boolean printLettersGuessed(GameState gameState) {
        String word = gameState.getWord(); // Get the word to be guessed
        Set<Character> guessedLetters = gameState.getGuessedLetters(); // Get the set of guessed letters
        int correctGuesses = 0; // Counter for correctly guessed letters

        // Loop through each character in the word
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i); // Get the current character

            // Check if the current character has been guessed
            if (guessedLetters.contains(currentChar)) {
                System.out.print(currentChar); // Print the correctly guessed letter
                correctGuesses++; // Increment the counter for correct guesses
            } else {
                System.out.print("-"); // Print a dash for unguessed letters
            }
        }
        System.out.println(); // Move to the next line after displaying the word

        // Return true if all letters in the word have been correctly guessed
        return correctGuesses == word.length();
    }
}

// Class to represent the state of the Hangman game
class GameState {
    private final String word; // The word to be guessed
    private final Set<Character> guessedLetters; // Set of letters guessed by the player
    private int wrongGuesses; // Counter for incorrect guesses

    // Constructor to initialize the game state with the chosen word
    public GameState(String word) {
        this.word = word; // Store the word to be guessed
        this.guessedLetters = new HashSet<>(); // Initialize the set of guessed letters
        this.wrongGuesses = 0; // Initialize the counter for wrong guesses
    }

    // Getter for the word
    public String getWord() {
        return word;
    }

    // Getter for the set of guessed letters
    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    // Getter for the number of wrong guesses
    public int getWrongGuesses() {
        return wrongGuesses;
    }

    // Method to add a guessed letter to the set
    public void addGuessedLetter(char letter) {
        guessedLetters.add(letter); // Add the letter to the set
    }

    // Method to increment the count of wrong guesses
    public void incrementWrongGuesses() {
        wrongGuesses++; // Increment the wrong guesses counter
    }
}
