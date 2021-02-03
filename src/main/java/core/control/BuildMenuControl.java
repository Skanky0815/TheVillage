package core.control;

import core.game.ui.GamePanel;

public class BuildMenuControl {

	private static BuildMenuControl instance;

	private final GameKeyListener keyListener;
	
	private int upDown;

    private int leftRight;

	private BuildMenuControl() {
		keyListener = GameKeyListener.getInstance();
	}

	public static BuildMenuControl getInstance() {
		if (null == instance) {
			instance = new BuildMenuControl();
		}
		return instance;
	}
	
	public void selectElement() {
		
		if (keyListener.isUpReleased()) {
            upDown = +1;
		}
		
		if (keyListener.isLeftReleased()) {
            leftRight = -1;
		}
		
		if (keyListener.isRightReleased()) {
            leftRight = 1;
		}
		
		if (keyListener.isDownReleased()) {
            upDown = -1;
		}
		
		if (keyListener.isSpaceReleased()) {
            GamePanel.player.build();
            keyListener.setBKey(false);
		}
	}

	public int getBuildingTyp() {
		int out = leftRight;
        leftRight = 0;
		return out;
	}

	public int getBuilding() {
		int out = upDown;
        upDown = 0;
		return out;
	}
}
