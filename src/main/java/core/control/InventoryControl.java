package core.control;

public class InventoryControl {

	private static InventoryControl instance;
	
	private final GameKeyListener keyListener;
	
	private int upDown;
	
	private InventoryControl() {
		keyListener = GameKeyListener.getInstance();
	}

	public static InventoryControl getInstance() {
		if (null == instance) {
			instance = new InventoryControl();
		}
		return instance;
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
