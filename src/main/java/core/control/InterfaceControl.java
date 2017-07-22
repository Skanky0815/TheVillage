package core.control;

import core.game.ui.DialogBox;
import core.helper.GuiDebugger;

public class InterfaceControl {

	private static InterfaceControl instance;
	
	private GameKeyListener keyListener;
	
	private boolean showInventory = false;

	private boolean showBuildmenu = false;

	private boolean showDialogBox = false;

    private boolean showDebuggingBox = false;
	
	private InterfaceControl() {
        keyListener = GameKeyListener.getInstance();
	}

	public static InterfaceControl getInstance() {
		if (instance == null) {
			instance = new InterfaceControl();
		}
		return instance;
	}
	
	public boolean isShowInventory() {
		return showInventory;
	}
	
	public boolean isShowBuildmenu() {
		return showBuildmenu;
	}
	
	public boolean isShowDialogBox() {
		return showDialogBox;
	}

    public boolean isShowDebuggingBox() {
        return showDebuggingBox;
    }
	
	public boolean showInterface() {
		if (keyListener.isIKey()) {
			showInventory = true;
			return true;
		} else {
			showInventory = false;
		}
		
		if (keyListener.isBKey()) {
			showBuildmenu = true;
			return true;
		} else {
			showBuildmenu = false;
		}
		
		if (DialogBox.getInstance().isShowed()) {
			showDialogBox = true;
			return true;
		} else {
			showDialogBox = false;
		}

        if (keyListener.isF1Key()) {
            showDebuggingBox = true;
            GuiDebugger.setGuiDebugger(true);
            return true;
        } else {
            showDebuggingBox = false;
            GuiDebugger.setGuiDebugger(false);
        }
		
		return false;
	}
}
