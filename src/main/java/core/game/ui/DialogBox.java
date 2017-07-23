package core.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.google.inject.Inject;
import core.control.DialogControl;
import core.game.dialog.Dialog;
import core.game.dialog.Dialog.Action;
import core.engine.Drawable;
import core.game.unit.NonPlayerCharacter;

public class DialogBox extends Rectangle2D.Double implements Drawable {

	private final DialogControl dialogControl;

	private boolean isShowed;

	private NonPlayerCharacter npc;

	private Dialog dialog;

	private int currentOption;

	@Inject
	private DialogBox(final DialogControl dialogControl) {
		this.dialogControl = dialogControl;
		width = 300;
		height = 100;
		currentOption = 0;

		isShowed = false;
	}

	public void setDialog() {
		dialog = dialog.getOptions().get(currentOption);
	}

	private void setDialog(final Dialog dialog) {
		this.dialog = dialog;
	}

	public void setIsShowed(final boolean b) {
		isShowed = b;
	}

	public boolean isShowed() {
		return isShowed;
	}

	public void setNpc(final NonPlayerCharacter npc) {
		this.npc = npc;
		dialog = npc.doSpeak();
		x = npc.getX();
		y = npc.getY();
	}

	public void doActions() {
		final Action action = dialog.getOptions().get(currentOption).getAction();
        switch (action) {
            case RETURN:
                this.setDialog(dialog.getParent());
                break;
            case TALK:
                this.setDialog();
                break;
            case DO_NPC_ACTION:
                this.setDialog();
                npc.setDoable(dialog.getDoable());
            case DO_PLAYER_ACTION:

            case QUIT:
                isShowed = false;
                break;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO implement the gui for a dialog
		g.setColor(Color.WHITE);
		g.fillRect((int) x - 100, (int) y + 65, (int) width, (int) height);

		g.setColor(Color.BLACK);
		g.drawString(npc.getName(), (int) x - 100, (int) y + 70);
		drawDialog(g);
	}

	private void drawDialog(final Graphics g) {
		this.validateInput(dialogControl.getSelected());

		g.drawString(dialog.getDialog(), (int) x - 100, (int) y + 95);

		final List<Dialog> options = dialog.getOptions();
		for (int i = 0; i < options.size(); i++) {
			int y = (int) this.y + 95 + (16 * (i + 1));

			g.drawString(options.get(i).getOption(), (int) x - 85, y);
			if (currentOption == i) {
				g.drawString(">", (int) x - 95, y);
			}
		}
	}

	private void validateInput(final int selected) {
		final int optionSeize = dialog.getOptions().size();
		currentOption = currentOption + selected;

		if (currentOption >= optionSeize) {
			currentOption = 0;
		}

		if (currentOption < 0) {
			currentOption = optionSeize - 1;
		}
	}
}