package core.game.ui;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import core.engine.Drawable;
import core.helper.ImageLoader;

public class PlayerBar extends Rectangle2D.Double implements Drawable {

	private static final long serialVersionUID = 1L;

	private static PlayerBar instance;

    private Map<String, BufferedImage[]> guiImage;
	

	private PlayerBar() {
        guiImage = ImageLoader.getPlayerBarGUI();

        x = GameFrame.pWidth - 300;
        y = GameFrame.pHeight - 300;
	}
	
	public static PlayerBar getInstance() {
		if (instance == null) {
			instance = new PlayerBar();
		}
		return instance;
	}

	public void draw(Graphics g) {
		g.drawImage(guiImage.get("background")[0], (int) x, (int) y, null);
	}
}
