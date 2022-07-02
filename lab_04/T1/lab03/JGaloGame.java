/**

 * JGaloFunction for JGalo.java
 * code from pds group 207 developed by:
 * Henrique Sousa, 98324 & Vítor Dias, 98396

 */


//package lab3;

public class JGaloGame implements JGaloInterface{
	private char player1;
    private char player2;
    private char[][] table;
    private char currentPlayer, winner;

    //Constructor if player gives argument
    public JGaloGame(char initialChar){
        this.player1 = 'X';
        this.player2 = 'O';
        this.table = new char[3][3];
        this.currentPlayer = initialChar;
        this.winner = ' ';
    }
    //Constructor if no argument is given
    public JGaloGame(){
        this.player1 = 'X';
        this.player2 = 'O';
        this.table = new char[3][3];
        this.currentPlayer = 'X';
        this.winner = ' ';
    }

    @Override
    public char getActualPlayer() {
        return this.currentPlayer;
    }

    @Override
    public boolean setJogada(int lin, int col) {
        this.table[lin - 1][col - 1] = this.currentPlayer;
        this.currentPlayer = (this.currentPlayer == this.player1) ? this.player2 : this.player1;
        return false;
    }

    @Override
    public boolean isFinished() {
        int count;
        char letter;
        //Check rows and columns
        for(int h = 1;h < 3; h++){
            for (int i = 0; i < 3; i++){
                count = 1;
                if(h == 1) letter = this.table[0][i];
                else letter = this.table[i][0];
                for (int j = 1; j < 3; j++){
                    if((h == 1 && Character.isLetter(this.table[j][i]) && letter == this.table[j][i]) || 
                    (h == 2 && Character.isLetter(this.table[i][j]) && letter == this.table[i][j])) count++; //If this space of the line/column is
            }                                                                               //the same as the first char, increase count 
                if (count == 3) {this.winner = letter; return true;}
            }
        }
        //Diagonal to downright
        letter = this.table[0][0];
        count = 1;
        for (int j = 1; j < 3; j++){
            if(Character.isLetter(this.table[0 + j][0 + j]) && letter == this.table[0 + j][0 + j]){
                count++;
            }
        }
        if (count == 3) {this.winner = letter; return true;}
        //Check Diagonal  downleft
        letter = this.table[0][2];      
        count = 1;
        for (int j = 1; j < 3; j++){
            if(Character.isLetter(this.table[0 + j][2 - j]) && letter == this.table[0 + j][2 - j]){
                count++;
            }
        }
        if (count == 3)  {this.winner = letter; return true;}
        //Check draw
        for(char[] bt : this.table) for(char c : bt) if (c == '\0') return false;
        //Then it's a draw
        return true;
    }

    @Override
    public char checkResult() {
        return this.winner;
    }
}
