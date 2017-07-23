package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.game.ui.DialogBox;
import core.helper.GuiDebugger;

@Singleton
public class InterfaceControl {

	private final GameKeyListener keyListener;
	private final DialogBox dialogBox;

	private boolean showInventory = false;

	private boolean showBuildmenu = false;

	private boolean showDialogBox = false;

    private boolean showDebuggingBox = false;

    @Inject
	private InterfaceControl(final GameKeyListener keyListener, final DialogBox dialogBox) {
        this.keyListener = keyListener;
		this.dialogBox = dialogBox;
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
		
		if (dialogBox.isShowed()) {
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
