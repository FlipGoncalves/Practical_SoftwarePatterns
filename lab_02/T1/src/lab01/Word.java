package lab01;

import java.util.Comparator;				// P1, G4 unnecessary

public class Word implements Comparable<Word> {
	private final String word;
	private int x;
	private int y;
	private Direction direction;

	public Word(String word) {
		this.word = word;
		this.x = -1;
		this.y = -1;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection(){
		return this.direction;
	}

	public char getCharAt(int index){
		// returns upper case char at position index of the word
		return Character.toUpperCase(this.word.charAt(index));
	}

	public int getWordLength() {
		return this.word.length();
	}

	public String getWord() {
		return this.word;
	}

	public int compareTo(Word word) {
		return word.getWordLength() - this.getWordLength();
	}

	public int getLastX(){
		return x + (this.getWordLength() - 1) * this.direction.getX();		// P1, G4 Code not used
	}

	public int getLastY(){
		return y + (this.getWordLength() - 1) * this.direction.getY();		// P1, G4 Code not used
	}

	public boolean intersects(Direction direction, int wordLength, int y, int x){
		if (this.getX() < 0)
			return false;

		if (this.direction != direction)
			return false;

		// values that this word ranges from
		int minX = Math.min(this.getLastX(), this.getX());
		int maxX = Math.max(this.getLastX(), this.getX());
		int minY = Math.min(this.getLastY(), this.getY());
		int maxY = Math.max(this.getLastY(), this.getY());

		// values of last letter of the other word
		int lastX = x + (wordLength - 1) * direction.getX();
		int lastY = y + (wordLength - 1) * direction.getY();

		// true if the positional values of the other word are in range of this one's
		return ((x >= minX && x <= maxX) && y >= minY && y <= maxY) ||
				((lastX >= minX && lastX <= maxX) && lastY >= minY && lastY <= maxY);			// P1, G4 Not legible code
	}

	@Override
	public String toString() {
		return String.format("%-15s %-6d %d,%-3d\t%s", word, word.length(), this.y + 1, this.x + 1, direction);
	}
}
