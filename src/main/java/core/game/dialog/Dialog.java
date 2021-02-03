package core.game.dialog;

import java.util.ArrayList;
import java.util.List;

import core.game.structures.environment.Resource;
import core.game.unit.NonPlayerCharacter;
import core.game.unit.actions.DoCollect;
import core.game.unit.actions.DoNothing;
import core.game.unit.actions.Doable;
import core.helper.Translator;

/**
 * 
 * @author RICO
 * TODO implement reading an xml file with dialogs
 */
public class Dialog {

	public enum Action {
		QUIT, RETURN, DO_NPC_ACTION, DO_PLAYER_ACTION, TALK
	}

	private Dialog parent;

    private Class ofClass;

    private Doable doable;

	private final String dialog;

	private String option;

	private Action action;

	private final List<Dialog> options;

	public Dialog(final String dialog) {
		this.dialog = dialog;
        parent = null;
        option = "";

        options = new ArrayList<>();
	}

    private Dialog(final String dialog, final String option, final Dialog parent) {
        this(dialog);
        this.option = option;
        this.parent = parent;
    }

	public Dialog(final String dialog, final String option, final Dialog parent, final Action action) {
		this(dialog, option, parent);
		this.action = action;
	}

    public <T> Dialog(final String dialog, final String option, final Dialog parent, final Action action, final Class<T> ofClass) {
        this(dialog, option, parent, action);
        this.ofClass = ofClass;
    }

    public Dialog(final String dialog, final String option, final Dialog parent, final Action action, final Doable doable) {
        this(dialog, option, parent, action);
        this.doable = doable;
    }

	public static Dialog returnOption(final Dialog parent) {
		final String option = Translator.translate("dialog.default.option.return");
		return new Dialog("", option, parent, Action.RETURN);
	}

	public static Dialog quitOption(final Dialog parent) {
		final String option = Translator.translate("dialog.default.option.quit");
		return Dialog.quitOption(parent, option);
	}

	private static Dialog quitOption(final Dialog parent, final String option) {
		return new Dialog("", option, parent, Action.QUIT);
	}

	public void addOption(final Dialog dialog) {
		options.add(dialog);
	}

	public List<Dialog> getOptions() {
		return options;
	}

	public String getDialog() {
		return dialog;
	}

	public String getOption() {
		return option;
	}

	public Dialog getParent() {
		return parent;
	}

	public Action getAction() {
		return action;
	}

    @SuppressWarnings("unchecked")
    public Doable getDoable() {
        if (null != ofClass) {
            if (Resource.class.isAssignableFrom(ofClass)) {
                return new DoCollect(ofClass);
            }

            if (NonPlayerCharacter.class.isAssignableFrom(ofClass)) {
                return new DoNothing(); // TODO Replace with a doable for fight
            }

        } else if (null != doable) {
            return doable;
        }

        return new DoNothing();
    }
}