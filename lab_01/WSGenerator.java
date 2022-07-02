import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WSGenerator {

	public static void main(String[] args) {

		Scanner data = null;
		try {
			data = new Scanner(new FileReader(args[0])); 		
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!"); System.exit(1);
		}
		
		String[] words; //Puzzle words in one line
		ArrayList<String> puzzleWords = new ArrayList<String>(); //All puzzle words
		int maxWordSize = 0;
		
		String line; char[] letters;
		
		//Retrieve data
		while (data.hasNextLine()) {
			line = data.nextLine();
			words = line.split("[;, ]");
			
			//Retrieve all puzzle words
			for (int j = 0; j < words.length; j++) {
				//Check if lower case
				if (!words[j].matches(".*[a-z]")) {
					System.out.println("Error: Puzzle words must have (at least) one lowercase letter!");
					data.close(); return ;
				}
				
				//Check if the characters are alphabetic
				letters = words[j].toCharArray();
				for (int i = 0; i < letters.length; i++) {
					if (!(Character.isAlphabetic(letters[i]))) {
						System.out.println("Error: Words must be alphabetic!");
						data.close(); return ;
					}
				}
				
				//Add word to puzzleWords
				puzzleWords.add(words[j].toUpperCase());
				
				//Save size of the longest word
				if (words[j].length() > maxWordSize)
					maxWordSize = words[j].length();
			}
		}
			
		//Obtain size
		if (!args[1].matches(".*[0-9]")) {
			System.out.println("Error: Input for size must be numeric!");
			return ;
		}
		int size = Integer.parseInt(args[1]);
		
		if (size > 40 || size < 0 || size < maxWordSize) {
			System.out.println("Error: Invalid size!");
			return ;
		}
		
		int row, col; String move;
		
		String[] moves = {"Up", "Left", "Down", "Right", "UpLeft", "UpRight", "DownRight", "DownLeft"};			
		Random random = new Random();
		
		//Initialize puzzle answer
		char[][] puzzle = new char[size][size];
		for (row = 0; row < size; row++) {
            for (col = 0; col < size; col++)
            	puzzle[row][col] = '.';
		}
		
		boolean error; 
		for (String word: puzzleWords) {
			do {
				error = true;
				
				row = random.nextInt(size); 
				col = random.nextInt(size);
				move = moves[random.nextInt(moves.length)];
				
				//Testing
				//System.out.printf("%s - Row: %d; Col: %d; Move: %s\n", word, row, col, move);
				
				switch (move) {
				case "Left":
					if (col >= word.length())
        				error = checkLeft(word, row, col, puzzle);
					break;
					
				case "Right":
					if (size - col >= word.length())
        				error = checkRight(word, row, col, puzzle);
					break;
				
				case "Up":
					if (row >= word.length())
        				error = checkUp(word, row, col, puzzle);
					break;

				case "Down":
					if (size - row >= word.length())
        				error = checkDown(word, row, col, puzzle);
					break;
				
				case "UpLeft":
					if (row >= word.length() && col >= word.length())
						error = checkUpLeft(word, row, col, puzzle);
					break;
					
				case "UpRight":
					if (row >= word.length() && size - col >= word.length()) 
						error = checkUpRight(word, row, col, puzzle);
					break;	
				
				case "DownLeft":
					if (size - row >= word.length() && col >= word.length())
						error = checkDownLeft(word, row, col, puzzle);
					break;
					
				case "DownRight":
					if (size - row >= word.length() && size - col >= word.length())
						error = checkDownRight(word, row, col, puzzle);
					break;			
				}
				
				//Testing
				//System.out.printf("Error: %s\n", error?"Yes":"No");
				
			} while (error == true);   
			
			System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, move);
		}
		
		//Print puzzle
		System.out.println();
		for (row = 0; row < size; row++) {
			for (col = 0; col < size; col++) {
				System.out.print(puzzle[row][col]);
				if (puzzle[row][col] == '.')
					//Add random uppercase letter
					puzzle[row][col] = (char) ('A' + random.nextInt(26));			
			}
            System.out.println();
		}
		
		if (args.length == 3) {
			//Obtain file to save results 
			try {
				FileWriter endFile = new FileWriter(args[2]);
				
				for (int i = 0; i < size; i++) {
                    endFile.write(puzzle[i]);
                    endFile.write("\n");
                }
                for (String word: puzzleWords) {
                    endFile.write(word.toLowerCase());
                    endFile.write("\n");
                }
				endFile.close();
			} catch (IOException e) {
				System.out.println("File not found!"); System.exit(1);
			}
		}
	}
	
	private static boolean checkLeft(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row][col-i] == word.charAt(i) || puzzle[row][col-i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row][col - j] = word.charAt(j);
			}
		}
		return error;
	}
	
	private static boolean checkRight(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row][col+i] == word.charAt(i) || puzzle[row][col+i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row][col + j] = word.charAt(j);
			}
		}
		return error;
	}
	
	private static boolean checkUp(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row-i][col] == word.charAt(i) || puzzle[row-i][col] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row - j][col] = word.charAt(j);
			}
		}
		return error;
	}
	
	private static boolean checkDown(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row+i][col] == word.charAt(i) || puzzle[row+i][col] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row + j][col] = word.charAt(j);
			}
		}	
		return error;
	}
	
	private static boolean checkUpLeft(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row-i][col-i] == word.charAt(i) || puzzle[row-i][col-i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row - j][col - j] = word.charAt(j);
			}
		}	
		return error;
	}
	
	private static boolean checkUpRight(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 1; i < word.length(); i++) {
			if (!(puzzle[row-i][col+i] == word.charAt(i) || puzzle[row-i][col+i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row - j][col + j] = word.charAt(j);
			}
		}	
		return error;
	}
	
	private static boolean checkDownLeft(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row+i][col-i] == word.charAt(i) || puzzle[row+i][col-i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row + j][col - j] = word.charAt(j);
			}
		}	
		return error;
	}
	
	private static boolean checkDownRight(String word, int row, int col, char[][] puzzle) {
		boolean error = true;
		
		for (int i = 0; i < word.length(); i++) {
			if (!(puzzle[row+i][col+i] == word.charAt(i) || puzzle[row+i][col+i] == '.'))
				break;
			
			//If we can add the word
			if (i == word.length()-1) {
				error = false;				
				//Save to puzzle
				for (int j = 0; j < word.length(); j++)
					puzzle[row + j][col + j] = word.charAt(j);
			}
		}	
		return error;
	}
}
