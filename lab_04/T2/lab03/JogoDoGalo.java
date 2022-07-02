package lab3;

public class JogoDoGalo implements JGaloInterface {
	private char actualPlayer;   // current player
	private Character[][] board;
	private char result;

	public JogoDoGalo(char actualPlayer) {
		this.actualPlayer = actualPlayer;
		this.board = new Character[3][3];
	}

	public boolean setJogada(int lin, int col) {
		lin--; // convert from 1-indexed to 0-indexed
		col--;
		if (this.board[lin][col] != null) // check if square is empty
			return false;			      // if not, return false (invalid move)
		this.board[lin][col] = this.actualPlayer; // if so, update board
		swapPlayer();
		return true; // valid move
	}

	public boolean isFinished() {
		Character[][] board = this.board;
		// swapPlayer just changed actualPlayer to the would-be next player
		char lastPlayer = (this.actualPlayer == 'X') ? 'O' : 'X'; // get the last player
		int n = board.length;
		boolean finished;
		// check rows
		for (int row = 0; row < n; row++) { // for every row
			finished = true;				// set finished to true
			for (int col = 0; col < n; col++) // for every column
				// if the square isn't lastPlayer, set finished to false
				if (board[row][col] == null || board[row][col] != lastPlayer)
					finished = false;
			if (finished) { // if not finished, check columns and diagonals
				this.result = lastPlayer;
				return true;
			}
		}
		// check columns
		for (int col = 0; col < n; col++) { // for every column
			finished = true;				// set finished to true
			for (int row = 0; row < n; row++) // for every row
				if (board[row][col] == null || board[row][col] != lastPlayer)
					finished = false;
			if (finished) {
				this.result = lastPlayer;
				return true;
			}
		}
		// check diagonals
		// upleft - downright diagonal (row = col)
		finished = true;
		for (int i = 0; i < n; i++)
			if (board[i][i] == null || board[i][i] != lastPlayer)
				finished = false;
		if (finished) {
			this.result = lastPlayer;
			return true;
		}
		// upright - downleft diagonal (col = n - row - 1)
		finished = true;
		int col;
		for (int row = 0; row < n; row++) {
			col = n - row - 1;
			if (board[row][col] == null || board[row][col] != lastPlayer)
				finished = false;
		}
		if (finished) {
			this.result = lastPlayer;
			return true;
		}
		boolean full = true;
		for (Character[] row : board)
			for (Character c : row)
				if (c == null)
					full = false;
		this.result = ' ';
		return full;
	}

	public char checkResult() {
		return this.result;
	}

	private void swapPlayer() {
		this.actualPlayer = (this.actualPlayer == 'X') ? 'O' : 'X';
	}

	public char getActualPlayer() {
		return this.actualPlayer;
	}
}