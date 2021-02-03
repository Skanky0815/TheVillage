package core.engine;

public enum MoveTo {
	N(0, -1),
	E(1, 0),
	S(0, 1),
	W(-1, 0);

	private final int x;
	
	private final int y;
	
	MoveTo(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
