package lab01;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class WSGenerator {
    public static void main(String[] args) throws IOException{

        ArrayList<Word> wordList = new ArrayList<>();

        int fileIndex = Arrays.asList(args).indexOf("-i") + 1;
        int outputIndex = Arrays.asList(args).indexOf("-o") + 1;
        int sizeIndex = Arrays.asList(args).indexOf("-s") + 1;

        // P1, G4 Verifica se introduz "-i", mas não verifica se introduz nome do ficheiro depois de "-i"
        // P1, G4 Não verifica se é introduzido "-o"
        
        if (fileIndex <= 0){                                                                    
            System.out.println("Nenhum ficheiro declarado\nUtilização:\t-i [filepath]");
            return;
        }
        if (sizeIndex <= 0){
            System.out.println("Não foi declarado um tamanho para o puzzle\nUtilização:\t-s [tamanho]");
            return;
        }
        
        int size;
        try {
            size = Integer.parseInt(args[sizeIndex]);               // P1, G4 Não verifica se é maior do que 40
        } catch (NumberFormatException e){
            System.out.println("O tamanho declarado não é válido\nUtilização:\t-s [tamanho]");
            return;
        }


        try {
            System.out.println(args[fileIndex]);
            File f = new File(args[fileIndex]);
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] words = line.split("[, ;]+");
                    for (String word: words) {
                        wordList.add(new Word(word));
                    }
                }
            sc.close();

        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        char[][] puzzle = new char[size][size];
        for (int i = 0; i < size; i++) {
        	for (int j = 0; j < size; j++) {
        		puzzle[i][j] = '.';
        	}
        }

        Random r = new Random();
        
        for (Word word: wordList) {
        	if (size < word.getWordLength()) {
        		System.out.println("Tamanho de puzzle insuficiente! Escolheste um valor menor que o tamanho desta palavra!");
        		return;
        	}
        	int x, y, dir;
        	Direction direction;
        	while (true) {     //ciclo while para verificar se os valores aleatórios são possíveis, ou seja, se escrever a palavra nessa direção não passa dos limites do array
        		dir = r.nextInt(8);
        		x = r.nextInt(size);
        		y = r.nextInt(size);
        		direction = Direction.values()[dir];
        		if (x + direction.getX() * word.getWordLength() < size && y+direction.getY()*word.getWordLength()<size && x+direction.getX()*word.getWordLength()>=0 && y+direction.getY()*word.getWordLength() >= 0) {
        			if (checkAvailability(word, puzzle, y, x, direction, 0))
        			    break;
        		}
        	}
        	writeWord(word, puzzle, y, x, direction, 0);
        }
        BufferedWriter out;
        if (outputIndex > 0)
            out = new BufferedWriter(new FileWriter("lab01/" + args[outputIndex]));
        else
            out = new BufferedWriter(new OutputStreamWriter(System.out));
        out.write("");
        
        for (int i=0; i<size; i++) {
        	for (int j=0; j<size; j++) {
        		if (puzzle[i][j]=='.') {
        			char c = (char) ('A' + r.nextInt(26));
        			puzzle[i][j]=c;
        		}
        		if (outputIndex <= 0) {
        			System.out.print(puzzle[i][j]);
        		}
        		else {
        			out.append(puzzle[i][j]);
        		}
        	}
        	if (outputIndex <= 0) {
        		System.out.println();
        	}
        	else {
        		out.append("\n");
        	}
        }
        
        try {
            File f = new File(args[fileIndex]);
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (outputIndex <= 0) {
                	System.out.println(line);
                }
                else {
                	 out.append(line);
                	 out.append("\n");
                }
               
            }
            sc.close();

        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        out.close();
    }

	public static boolean checkAvailability(Word word, char[][] puzzle, int y, int x, Direction direction,	int currentIndex) {
        if (puzzle[y][x] != '.' && puzzle[y][x] != word.getCharAt(currentIndex)) {
            return false;
        }
		if (currentIndex == word.getWordLength() - 1)
            return true;
		return checkAvailability(word, puzzle, y+direction.getY(), x+direction.getX(), direction, currentIndex+1);

	}

	public static void writeWord(Word word, char[][] puzzle, int y, int x, Direction direction, int currentIndex) {
		if (currentIndex<word.getWordLength()) {
			puzzle[y][x]=word.getCharAt(currentIndex);
			writeWord(word, puzzle, y+direction.getY(), x+direction.getX(), direction, currentIndex+1);
		}
	}
}
