package core.helper;

public class GuiDebugger {
	
	private static boolean debug;
	
	public static void setGuiDebugger(final String b) {
		debug = Integer.parseInt(b) == 1;
	}

    public static void setGuiDebugger(final boolean b) {
        debug = b;
    }
	
	public static boolean isDebugModeOn() {
		return debug;
	}

}
