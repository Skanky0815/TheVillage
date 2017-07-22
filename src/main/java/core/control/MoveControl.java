package core.control;

import core.game.ui.GamePanel;

public final class MoveControl {

	private static MoveControl instance;
	
	private GameKeyListener keyListener;
	
	private MoveControl() {
        keyListener = GameKeyListener.getInstance();
	}

	public static MoveControl getInstance() {
		if (instance == null) {
			instance = new MoveControl();
		}
		return instance;
	}
	
	public boolean move() {
		boolean moved = false;
		
		if (keyListener.isUp()) {
			GamePanel.player.moveUp();
			moved = true;
		}
		
		if (keyListener.isLeft()) {
            GamePanel.player.moveLeft();
			moved = true;
		}
		
		if (keyListener.isDown()) {
            GamePanel.player.moveDown();
			moved = true;
		}
		
		if (keyListener.isRight()) {
            GamePanel.player.moveRight();
			moved = true;
		}

		if (!keyListener.isUp() && !keyListener.isDown()) {
            GamePanel.player.moveUpDownStop();
			moved = true;
		}
		
		if (!keyListener.isLeft() && !keyListener.isRight()) {
            GamePanel.player.moveLeftRightStop();
			moved = true;
		}
		
		return moved;
	}
}
