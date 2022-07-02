import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class WSSolver {

	public static void main(String[] args) {

		Scanner data = null;
		try {
			data = new Scanner(new FileReader(args[0]));			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!"); System.exit(1);
		}
			
		String[] words; //Puzzle words in one line
		ArrayList<String> puzzleWords = new ArrayList<String>(); //All puzzle words
		
			//Retrieve data
		
		int nLine = -1;
		String line; char[] letters;
		
		//Max size
		int size = 40;
		char[][] puzzle = new char[size][size];
		
		boolean firstLine = true;
		boolean puzzleEnd = false;
		
		while(data.hasNextLine()) {
			line = data.nextLine(); nLine++;
			
			//Check if the current line is empty
			if (line.isEmpty()) {
				data.close(); return ;
			}
							
			//First line
			if (firstLine) { //or nLine == 0;					
				//All (puzzle) lines must have the same number of letters as the first line
				size = line.length(); //or size = letters.lenght;
				
				//Check if the size of the first line is valid
				if (size > 40 || size < 0) {
					data.close(); return ;
				}
				
				puzzle = new char[size][size];
				firstLine = false;
			}
			
			// Check if we reached the end of the puzzle				
			if (line.matches(".*[a-z]") && puzzleEnd == false) {
				puzzleEnd = true;

				//Check if puzzle is square
				if (nLine != size) {
					System.out.println("Error: Puzzle with invalid size!");
					data.close(); return ;
				}
			}	
			
			//Puzzle
			if (puzzleEnd == false) {	
				letters = line.toCharArray();
				//Check if puzzle has valid size
				if (line.length() != size || nLine > size - 1) {
					System.out.println("Error: Puzzle with invalid size!");
					data.close(); return ;
				}
						
				//Check if the characters are alphabetic and upper case
				for (int i = 0; i < size; i++) {
					if (!(Character.isAlphabetic(letters[i]) && Character.isUpperCase(letters[i]))) {
						System.out.println("Error: Puzzle must be alphabetic/uppercase!");
						data.close(); return ;
					}
				}
				
				puzzle[nLine] = letters;
			}
			
			//Words to look for
			if (puzzleEnd == true) {
				words = line.split("[;, ]");
						
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
				}
			}
		}
		data.close();
		
		//Find answers
		solve(puzzleWords, puzzle, size);
	}
	
	
	private static void solve(ArrayList<String> puzzleWords, char[][] puzzle, int size) {
		
		//Initialize array answer
		char[][] answer = new char[size][size];
		for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
            	answer[row][col] = '.';
            }
		}
		
		for (String word: puzzleWords) {
			char firstLetter = word.charAt(0);
			boolean found = false;
			
	        for (int row = 0; row < size; row++) {
	            for (int col = 0; col < size; col++) {
	            	
	            	//Find first letter
	            	if (puzzle[row][col] == firstLetter && found == false) {
	            		
	        			//If we can move left
	        			if (col >= word.length() && found == false)
	            			found = checkLeft(word, row, col, puzzle, answer);
	        			
	        			//If we can move right
	        			if (size - col >= word.length() && found == false)
	        				found = checkRight(word, row, col, puzzle, answer);
	            		
	        			//If we can move up
	        			if (row >= word.length() && found == false)
	        				found = checkUp(word, row, col, puzzle, answer);
	        			
	        			//If we can move down
	        			if (size - row >= word.length() && found == false)
	        				found = checkDown(word, row, col, puzzle, answer);      			
	        			
	        			//If we can move up left
	        			if (row >= word.length()&& col >= word.length() && found == false)
	            			found = checkUpLeft(word, row, col, puzzle, answer);
	        			
	        			//If we can move up right
	        			if (row >= word.length() && size - col >= word.length() && found == false)
	        				found = checkUpRight(word, row, col, puzzle, answer);
	        			
	        			//If we can move down left
	        			if (size - row >= word.length() && col >= word.length() && found == false)
	        				found = checkDownLeft(word, row, col, puzzle, answer);
	        			
	        			//If we can move down right
	        			if (size - row >= word.length() && size - col >= word.length() && found == false) {
	        				found = checkDownRight(word, row, col, puzzle, answer);
	            		}	
	            	}  	
	            } 
	        }
	        
		}    	
		
		//Print answer
		System.out.println();
		for (int i = 0; i < size; i++)
			System.out.println(answer[i]);
        		
	}
	
	private static boolean checkLeft(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row][col-i] != word.charAt(i)) 
				break;
			
			// Check if intersects with another word
			if(answer[row][col-i] == word.charAt(i))
				count++;

			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "Left"); 					

				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row][col - j] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkRight(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row][col+i] != word.charAt(i))
				break;
			
			// Check if intersects with another word
			if(answer[row][col+i] == word.charAt(i))
				count++;	

			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "Right"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
		        	answer[row][col + j] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkUp(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row-i][col] != word.charAt(i))
				break;
			
			// Check if intersects with another word
			if(answer[row-i][col] == word.charAt(i))
				count++;

			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "Up"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row - j][col] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkDown(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row+i][col] != word.charAt(i))
				break;
			
			// Check if intersects with another word
			if(answer[row+i][col] == word.charAt(i))
				count++;
			
			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "Down"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row + j][col] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkUpLeft(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row-i][col-i] != word.charAt(i))
				break;
			
			// Check if intersects with another word
			if(answer[row-i][col-i] == word.charAt(i))
				count++;
			
			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "UpLeft"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row-j][col-j] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkUpRight(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;

		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row-i][col+i] != word.charAt(i))
				break;
			
			// Check if intersects with another word
			if(answer[row-i][col+i]  == word.charAt(i))
				count++;
			
			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "UpRight"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row-j][col+j] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	private static boolean checkDownLeft(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row+i][col-i] != word.charAt(i))
				break;

			// Check if intersects with another word	
			if(answer[row+i][col-i] == word.charAt(i))
				count++;
			
			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "DownLeft"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row+j][col-j] = word.charAt(j);
			}
		}
		
		return found;
	}
	
	
	private static boolean checkDownRight(String word, int row, int col, char[][] puzzle, char[][] answer) {
		boolean found = false;
		int count = 1;
		
		for (int i = 1; i < word.length(); i++) {
			if (puzzle[row+i][col+i] != word.charAt(i))
				break;
			
			// Check if intersects with another word	
			if(answer[row+i][col+i] == word.charAt(i))
				count++;
			
			//If it matches
			if (i == word.length()-1 && count != word.length()) {
				found = true;
				System.out.printf("%-40s %-2d %2d,%-2d %-10s\n", word, word.length(), row+1, col+1, "DownRight"); 
				
				//Save to answer
				for (int j = 0; j < word.length(); j++)
					answer[row+j][col+j] = word.charAt(j);
			}
		}
		
		return found;
	}
}
