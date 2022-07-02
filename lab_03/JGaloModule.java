public class JGaloModule implements JGaloInterface {
    private String name;
    private char actualPlayer;  // Or private char actualPlayer = 'X';
    private char[][] panel = new char[3][3];
    private char winner = ' ';

    public JGaloModule(String name, char actualPlayer) {
        this(name);
        this.actualPlayer = actualPlayer;
    }

    public JGaloModule(String name) {
        this.name = name;
        if (actualPlayer == 0)      // If the actualPlayer is undefined
            actualPlayer = 'X';     // Use predefined value (x)
    }
    
    public char getActualPlayer() {
        return this.actualPlayer;
    }

    public boolean setJogada(int lin, int col) {
        boolean validPlay = false;

        // If the box in question hasn't been checked
        if (panel[lin-1][col-1] == 0) {
            validPlay = true;
            panel[lin-1][col-1] = actualPlayer;
        } 

        return validPlay;
    }

	public boolean isFinished() {
        char firstBox;
        winner = getActualPlayer();
        
        // Check if the actual player has won
        for (int i = 0; i < 9; i++) {
            firstBox = panel[i/3][i%3];
            if (firstBox != 0) {
                // Check right
                if (i%3 == 0) {  
                    if (firstBox == panel[i/3][(i+1)%3] &&
                        firstBox == panel[i/3][(i+2)%3])
                        return true;
                }
                // Check down
                if (i < 3) {
                    if (firstBox == panel[i/3+1][i%3] &&
                        firstBox == panel[i/3+2][i%3])
                        return true;
                }
                
                // Check down-right
                if (i == 0) {
                    if (firstBox == panel[i/3+1][i%3+1] &&
                        firstBox == panel[i/3+2][i%3+2])
                        return true;
                }
                // Check up-right
                if (i == 6) {
                    if (firstBox == panel[i/3-1][i%3+1] &&
                        firstBox == panel[i/3-2][i%3+2])
                        return true;
                }
            }
        }

        // Check if all boxes were checked
        // Note: Do this last, in case a player wins after checking the last box
        for (int i = 0; i < 9; i++) {
            if (panel[i/3][i%3] == 0)
                break;
            else if (i == 8) {
                winner = ' '; return true;
            }
        }

        // If the game isn't finished
        if (actualPlayer == 'X')
                actualPlayer = 'O';
            else
                actualPlayer = 'X';
        winner = ' ';
        return false;
    }
	
    public char checkResult() {
        return this.winner;
    }
}
