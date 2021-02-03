package core.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import core.control.BuildMenuControl;
import core.engine.Drawable;
import core.game.item.Resource;
import core.game.item.Resources;
import core.game.item.ResourcesType;
import core.game.structures.Blueprint;
import core.helper.GameFont;
import core.helper.GameFont.GameFontTyp;
import core.helper.GuiDebugger;
import core.helper.ImageLoader;

public class BuildMenu extends Rectangle2D.Double implements Drawable {

	private static final long serialVersionUID = 1L;

	private static BuildMenu instance;
	
	private BuildMenuControl buildmenuControl;
	
	private Resources resources;
	
	private Map<String, BufferedImage[]> guiImage;
	
	private GameFont gameFont;

	private int buildingTyp;

	private int building;

	private BuildMenu() {
        width = 150;
        height = 50;

        buildingTyp = 0;
        building = 0;

        buildmenuControl = BuildMenuControl.getInstance();
        resources = Resources.getInstance();
        guiImage = ImageLoader.getBuildMenuGUI();
        gameFont = GameFont.getInstance();
	}

	public static BuildMenu getInstance() {
		if (instance == null) {
            instance = new BuildMenu();
		}
		return instance;
	}

	private Blueprint getBlueprint() {
		if (buildingTyp == GamePanel.buildableList.size()) {
            buildingTyp = 0;
		}
		
		if (buildingTyp < 0) {
            buildingTyp = GamePanel.buildableList.size() + buildingTyp;
		}
		
		if (building == GamePanel.buildableList.get(buildingTyp).size()) {
            building = 0;
		}
		
		if (building < 0) {
            building = GamePanel.buildableList.get(buildingTyp).size() + building;
		}

        GamePanel.player.setBuildingTyp(buildingTyp);
        GamePanel.player.setBuilding(building);
		
		return GamePanel.buildableList.get(buildingTyp).get(building);
	}
	
	private void getInfobox(final Graphics g) {
		Rectangle2D.Double infobox = new Rectangle2D.Double(x + 50, y + 50, 50, 100);
		int x = (int) (infobox.x - 25);
		int headTypoX = (int) (infobox.x - 20);
		int typoX = (int) (infobox.x + 2);
		int resourceIconX = (int) (infobox.x - 20);
		
		Blueprint blueprint = this.getBlueprint();
	
		g.drawImage(blueprint.getIcon()[0], (int) infobox.x, (int) y, null);

		g.setColor(Color.BLACK);
		
		g.setFont(this.gameFont.getFont(GameFontTyp.INFOBOX_HEADLINE));
		g.drawImage(this.guiImage.get("infobox_top")[0], x, (int) infobox.y, null);
		g.drawString(blueprint.getName(), headTypoX, (int) infobox.y + 18);
		
		int loopY = (int) infobox.y + 21;
		g.setFont(this.gameFont.getFont(GameFontTyp.INFOBOX));
		BufferedImage centerPic = this.guiImage.get("infobox_center")[0];
		for (final Map.Entry<ResourcesType, Integer> price : blueprint.getPriceList().entrySet()) {
			Resource resource = this.resources.getResource(price.getKey());
			
			if (GamePanel.player.canBuild(price.getKey(), price.getValue())) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}

			String str = price.getValue() + "x " + resource.getName();
			
			g.drawImage(centerPic, x, loopY, null);
			g.drawString(str, typoX, loopY + 14);
			g.drawImage(resource.getSIcon(), resourceIconX, loopY + 1, null);
			
			loopY = loopY + 16;
		}

		g.drawImage(guiImage.get("infobox_bottom")[0], x, loopY, null);
	}

	public void draw(Graphics g) {
        buildingTyp = buildingTyp + buildmenuControl.getBuildingTyp();
        building = building + buildmenuControl.getBuilding();

        x = GamePanel.player.getX() - 50;
        y = GamePanel.player.getY() + 50;
		
		g.drawImage(this.guiImage.get("background")[0], (int) x, (int) y, null);
		
		try {
			g.drawImage(GamePanel.buildableList.get(buildingTyp - 1).get(0).getIcon()[1], (int) x, (int) y, null);
		} catch(IndexOutOfBoundsException e) { 
			g.drawImage(
                    GamePanel.buildableList.get(GamePanel.buildableList.size() - 1).get(0).getIcon()[1],
                    (int) this.x,
                    (int) this.y,
                    null
            );
		}
		
		try{
			g.drawImage(
                    GamePanel.buildableList.get(this.buildingTyp + 1).get(0).getIcon()[1],
					(int) (x + (50 * 2)),
                    (int) y,
                    null
            );
		} catch(IndexOutOfBoundsException e) {
			g.drawImage(
                    GamePanel.buildableList.get(0).get(0).getIcon()[1],
					(int) (x + (50 * 2)),
                    (int) y,
                    null
            );
		}
		
		try {
			this.getInfobox(g);
		} catch(IndexOutOfBoundsException ignored) { }
		
		g.drawImage(guiImage.get("selection")[0], (int) (x + 50), (int) y, null);
		g.drawImage(guiImage.get("arrow_up")[0], (int) (x + 63.5), (int) (y - 5), null);
		g.drawImage(guiImage.get("arrow_down")[0], (int) (x + 63.5), (int) (y + 45), null);
		g.drawImage(guiImage.get("arrow_left")[0], (int) (x + 45), (int) (y + 13.5), null);
		g.drawImage(guiImage.get("arrow_right")[0], (int) (x + 95), (int) (y + 13.5), null);

		if (GuiDebugger.isDebugModeOn()) {
			g.setColor(Color.RED);
			g.drawRect((int) x, (int) y, (int) width, (int) height);
			g.drawRect((int) (x + 50), (int) y, 50, 50);
		}
	}
}
