package core.helper;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;


public class GameFont {

	private static GameFont instance;
	
	private Map<GameFontTyp, Font> gameFonts;	
	
	public enum GameFontTyp {
		INFOBOX_HEADLINE, INFOBOX
	}
	
	private GameFont() {
		String timesRoman = "Times New Roman";
		gameFonts = new HashMap<>();
		gameFonts.put(GameFontTyp.INFOBOX_HEADLINE, new Font(timesRoman, Font.BOLD, 16));
		gameFonts.put(GameFontTyp.INFOBOX, new Font(timesRoman, Font.PLAIN, 12));
	}

	public static GameFont getInstance() {
		if (instance == null) {
			instance = new GameFont();
		}
		return instance;
	}
	
	public Font getFont(GameFontTyp key) {
		return gameFonts.get(key);
	}
	
}
