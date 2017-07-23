package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.game.ui.GamePanel;

@Singleton
public class BuildMenuControl {

	private final GameKeyListener keyListener;
	
	private int upDown;

    private int leftRight;

    @Inject
	private BuildMenuControl(final GameKeyListener keyListener) {
		this.keyListener = keyListener;
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
