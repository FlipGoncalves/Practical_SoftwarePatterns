package lab01;

public enum Direction {
    Up          (-1,0),
    UpRight     (-1,1),
    Right       (0,1),
    DownRight   (1,1),
    Down        (1,0),
    DownLeft    (1,-1),
    Left        ( 0,-1),
    UpLeft      (-1,-1);

	private final int y;
	private final int x;

    Direction(int y, int x) {
    	this.y=y;
    	this.x=x;
    }

    public int getX() {
    	return x;
    }

    public int getY() {
    	return y;
    }

}
