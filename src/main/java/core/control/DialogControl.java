package core.control;

import core.game.ui.DialogBox;

public class DialogControl {

	private static DialogControl instance;
	
	private GameKeyListener keyListener;
	
	private int upDown;
	
	private DialogControl() {
		keyListener = GameKeyListener.getInstance();
	}

	public static DialogControl getInstance() {
		if (instance == null) {
			instance = new DialogControl();
		}
		return instance;
	}
	
	public void selectElement() {
		
		if (keyListener.isUpReleased()) {
			upDown = -1;
		}

		if (keyListener.isDownReleased()) {
			upDown = +1;
		}
		
		if (keyListener.isSpaceReleased()) {
			DialogBox.getInstance().doActions();
		}
	}
	
	public int getSelected() {
		int out = upDown;
		upDown = 0;
		return out;
	}
}