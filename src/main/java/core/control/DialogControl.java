package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.game.ui.DialogBox;

@Singleton
public class DialogControl {

	private final GameKeyListener keyListener;
	
	private int upDown;

	@Inject
	private DialogControl(final GameKeyListener keyListener) {
		this.keyListener = keyListener;
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