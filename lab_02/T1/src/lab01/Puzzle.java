package lab01;

import java.util.ArrayList;

public class Puzzle {
    private ArrayList<String> puzzle;

    public Puzzle(){
        puzzle = new ArrayList<>();             // P1, G4 Change to this.puzzle
    };

    public void add(String row){
        this.puzzle.add(row);
    }

    public String getRow(int y){
        return this.puzzle.get(y);
    }

    public char getChar(int y, int x){
        return this.puzzle.get(y).charAt(x);
    }

    public int getLength(){
        return this.puzzle.size();
    }

    public boolean checkIfIsSquare(){
        for (String row: this.puzzle) {
            if (row.length() != this.getLength())
                return false;
        }
        return true;
    }

}