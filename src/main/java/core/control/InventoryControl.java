package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class InventoryControl {
	
	private final GameKeyListener keyListener;
	
	private int upDown;

	@Inject
	private InventoryControl(final GameKeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void selectElements() {
		if (keyListener.isUpReleased()) {
			upDown = +1;
		}
		
		if (keyListener.isDownReleased()) {
			upDown = -1;
		}
	}
	
	public int getSelectedElement() {
		int out = upDown;
		upDown = 0;
		return out;
	}
}
