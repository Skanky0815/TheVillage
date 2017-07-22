package core.control;

public class InventoryControl {

	private static InventoryControl instance;
	
	private GameKeyListener keyListener;
	
	private int upDown;
	
	private InventoryControl() {
		keyListener = GameKeyListener.getInstance();
	}

	public static InventoryControl getInstance() {
		if (instance == null) {
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
