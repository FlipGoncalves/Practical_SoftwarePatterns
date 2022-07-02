package lab01;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WSSolver {

    public static boolean checkForMatch(Puzzle puzzle, Word word, Direction direction, int y, int x, int indexOfLetter) {

        // if letter from puzzle is different from the considered word letter
        if (word.getCharAt(indexOfLetter) != puzzle.getChar(y,x))
            // returns false value
            return false;

        // if current char is the last char of the word
        if (indexOfLetter == word.getWordLength() - 1)
            // returns true value
            return true;

        // waits for return value of next letters in this direction
        return checkForMatch(puzzle, word, direction, y + direction.getY(), x + direction.getX(), ++indexOfLetter);
    }

    public static boolean checkIfWordFits(Puzzle puzzle, int x, int y, Word word, Direction direction){
        int maxY = y + (word.getWordLength() - 1) * direction.getY();
        int maxX = x + (word.getWordLength() - 1) * direction.getX();
        return !(maxY < 0 || maxY >= puzzle.getLength() || maxX < 0 || maxX >= puzzle.getLength());
    }

    public static void insertWords(char[][] output, Word word, int y, int x, int indexOfLetter) {

        output[y][x] = word.getCharAt(indexOfLetter);

        if (++indexOfLetter < word.getWordLength())
            insertWords(output, word, y + word.getDirection().getY(), x + word.getDirection().getX(), indexOfLetter);
    }


    public static void main(String[] args){

        if (args.length < 1){
            System.out.print("Insira um file txt como argumento da chamada à função");
            return;
        }
        ArrayList<Word> wordList = new ArrayList<>();
        Puzzle puzzle = new Puzzle();

        try {
            File f = new File(args[0]);
            Scanner sc = new Scanner(f);   

            while (sc.hasNextLine()) {
                String line = sc.nextLine();    
                
                // P1, G4 Não verifica se os caracteres são alfabéticos
                // P1, G4 Não verifica se as linhas são vazias

                // if the line contains lower case letters (is part of the list of words)
                if (!line.matches(line.toUpperCase())){
                	String[] words = line.split("[, ;]+");
                	for (String word: words) {
                        wordList.add(new Word(word));                               
                	}
                    continue;
                }

                // if the line has more than 40 letters
                if(line.length() > 40){
                    System.out.println("Tamanho do puzzle acima do permitido");
                    // the program ends
                    return;
                }

                puzzle.add(line);
            }

            sc.close();

        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (!puzzle.checkIfIsSquare()){
            System.out.println("O puzzle não é quadrado.");
            return;
        }


        wordList.stream()
                .sorted()
                .forEach(word -> {
                    char letter = Character.toUpperCase(word.getCharAt(0));
        	        for (int y = 0; y < puzzle.getLength(); y++) {

                        String row = puzzle.getRow(y);
        	            int x = row.indexOf(letter);
        		        while( x >= 0) {

        			        for (Direction direction: Direction.values()){

        			            // if there's no space for the word to be in this direction
        			            if (!checkIfWordFits(puzzle, x, y, word, direction))
        			                continue;

                                // call of recursive function for each direction
                                if (!checkForMatch(puzzle, word, direction,y + direction.getY(),
                                        x + direction.getX(), 1))
                                    continue;

                                boolean intersects = false;

                                for (Word word1 : wordList) {
                                    if (word1.intersects(direction, word.getWordLength(), y, x)) {
                                        intersects = true;
                                    }
                                }

                                if (intersects)
                                    continue;

                                word.setDirection(direction);
                                word.setX(x);
                                word.setY(y);
                            }

        			        // we find the next position of the letter in the puzzle row
        			        x = row.indexOf(letter, x + 1);
                        }
                    }
                });

        char[][] output = new char[puzzle.getLength()][puzzle.getLength()];

        for (Word word: wordList) {
            if (word.getDirection() == null)
                continue;
            insertWords(output, word, word.getY(), word.getX(), 0);
        }
        try {
            PrintWriter writer = new PrintWriter("./lab01/out/out1.txt", StandardCharsets.UTF_8);       // P1, G4 ./src/ CHANGED

            wordList.forEach(writer::println);

            writer.println();
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < output.length; j++) {
                writer.print(output[i][j] != 0 ? output[i][j] : ".");
                }
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
