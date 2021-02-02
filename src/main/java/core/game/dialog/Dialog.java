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

    /**
	 * All actions that the player can select in a dialog
	 */
	public enum Action {
		QUIT, RETURN, DO_NPC_ACTION, DO_PLAYER_ACTION, TALK
	}

	private Dialog parent;

    private Class ofClass;

    private Doable doable;

	private String dialog;

	private String option;

	private Action action;

	private List<Dialog> options;

	/**
	 * Constructor for dialog
	 * 
	 * @param dialog String
	 */
	public Dialog(final String dialog) {
		this.dialog = dialog;
        parent = null;
        option = "";

        options = new ArrayList<Dialog>();
	}

    /**
     *
     * @param dialog String
     * @param option String
     * @param parent Dialog
     */
    private Dialog(final String dialog, final String option, final Dialog parent) {
        this(dialog);
        this.option = option;
        this.parent = parent;
    }

	/**
	 * Constructor for dialog as option
	 * 
	 * @param dialog String
	 * @param option String
	 * @param parent Dialog
	 * @param action Action
	 */
	public Dialog(final String dialog, final String option, final Dialog parent, final Action action) {
		this(dialog, option, parent);
		this.action = action;
	}

    /**
     * Constructor for dialog as option
     *
     * @param dialog String
     * @param option String
     * @param parent Dialog
     * @param action Action
     * @param ofClass Class
     */
    public <T> Dialog(final String dialog, final String option, final Dialog parent, final Action action, final Class<T> ofClass) {
        this(dialog, option, parent, action);
        this.ofClass = ofClass;
    }

    /**
     *
     * @param dialog String
     * @param option String
     * @param parent Dialog
     * @param action Action
     * @param doable Doable
     */
    public Dialog(final String dialog, final String option, final Dialog parent, final Action action, final Doable doable) {
        this(dialog, option, parent, action);
        this.doable = doable;
    }

	/**
	 * Return a default return option for the dialog to go back to the parent
	 * 
	 * @param parent Dialog
	 * @return Dialog with return action
	 */
	public static Dialog returnOption(final Dialog parent) {
		final String option = Translator.translate("dialog.default.option.return");
		return new Dialog("", option, parent, Action.RETURN);
	}

	/**
	 * Return a default quit option for the dialog
	 * 
	 * @param parent Dialog
	 * @return Dialog with quit action
	 */
	public static Dialog quitOption(final Dialog parent) {
		final String option = Translator.translate("dialog.default.option.quit");
		return Dialog.quitOption(parent, option);
	}

	/**
	 * Return a quit option with the given sentence
     *
	 * @param parent Dialog
	 * @param option String
	 * @return Dialog with quit action
	 */
	private static Dialog quitOption(final Dialog parent, final String option) {
		return new Dialog("", option, parent, Action.QUIT);
	}
	
	/**
	 * Add a option to this dialog
	 * 
	 * @param dialog Dialog
	 */
	public void addOption(final Dialog dialog) {
		options.add(dialog);
	}

	/**
	 * Return a List with all options from this dialog
	 * 
	 * @return List(Dialog) options
	 */
	public List<Dialog> getOptions() {
		return options;
	}

	/**
	 * Return the main dialog from this dialog
	 * 
	 * @return String dialog
	 */
	public String getDialog() {
		return dialog;
	}

	/**
	 * Return the option string from this dialog
	 * 
	 * @return String option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * Return the parent dialog from this dialog
	 * 
	 * @return Dialog parent
	 */
	public Dialog getParent() {
		return parent;
	}

	/**
	 * Return the action key
	 * 
	 * @return Action action
	 */
	public Action getAction() {
		return action;
	}

    /**
     * Return a Class that implements doable
     *
     * @return doable
     */
    @SuppressWarnings("unchecked")
    public Doable getDoable() {
        if (ofClass != null) {
            if (Resource.class.isAssignableFrom(ofClass)) {
                return new DoCollect(ofClass);
            }

            if (NonPlayerCharacter.class.isAssignableFrom(ofClass)) {
                return new DoNothing(); // TODO Replace with a doable for fight
            }

        } else if (doable != null) {
            return doable;
        }

        return new DoNothing();
    }
}