package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.game.ui.GamePanel;

@Singleton
public final class MoveControl {

	private final GameKeyListener keyListener;

	@Inject
	private MoveControl(final GameKeyListener keyListener) {
        this.keyListener = keyListener;
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
