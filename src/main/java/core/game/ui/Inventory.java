package core.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.google.inject.Inject;
import core.control.InventoryControl;
import core.engine.services.TranslateService;
import core.engine.services.game.InterfaceRenderInventoryService;
import core.engine.services.game.InventoryService;
import core.game.item.Resource;
import core.helper.GameFont;
import core.helper.ImageLoader;
import core.helper.StringBreaker;
import core.helper.GameFont.GameFontTyp;
import org.apache.logging.log4j.Logger;

public class Inventory extends AbstractGUI {

	private InventoryControl inventoryControl;

	private Map<String, BufferedImage[]> guiImage;
	
	private GameFont font;

	private int itemPointer = 0;

	private InterfaceRenderInventoryService inventoryService;

	@Inject
	public Inventory(final Logger logger, final TranslateService translator) {
		log = logger;
		this.translator = translator;

        x = GameFrame.pWidth - 150;
		y = 50;

		width = 200;
		height = 150;

		inventoryControl = InventoryControl.getInstance();
		guiImage = ImageLoader.getInventoryGUI();
		font = GameFont.getInstance();
	}

	public void draw(Graphics g) {
        itemPointer = itemPointer + inventoryControl.getSelectedElement();
		
		int x = (int) this.x;
		int y = (int) this.y;
		int iconX = (x + 5);
		int typoX = (x + 30);
		int arrowX = (int) (x + 36.5);

		g.drawImage(guiImage.get("list")[0], x, y, null);
		
		g.setColor(Color.BLACK);
		if (GamePanel.player.getBackpackSize() != 0) {
			try {
				final Resource resource = inventoryService.getResource(itemPointer);
				final String str = GamePanel.player.getBackpack().get(resource.getType())
						+ "x " + translator.translate(resource.getName());
				
				g.setFont(font.getFont(GameFontTyp.INFOBOX_HEADLINE));
				g.drawString(str, typoX, y + 85);
				
				int infoboxY = y + 150;
				int infoboxTypoX = x + 5;
				
				g.setFont(font.getFont(GameFontTyp.INFOBOX_HEADLINE));
				g.drawImage(guiImage.get("infobox_top")[0], x, infoboxY, null);
				g.drawImage(resource.getSIcon(), iconX, infoboxY + 5, null);
				g.drawString(translator.translate(resource.getName()), typoX, infoboxY + 18);
				
				int counter = 0;
				int infoboxCenterY = infoboxY + 22;
				int infoboxTextY = infoboxY + 36;
				g.setFont(font.getFont(GameFontTyp.INFOBOX));
				for (final String string : StringBreaker.beakString(
						translator.translate(resource.getDescription()),
						90,
						g
				)) {
					g.drawImage(guiImage.get("infobox_center")[0], x, infoboxCenterY + ( 16 * counter), null);
					g.drawString(string, infoboxTypoX, infoboxTextY + (16 * counter++));
				}
				
				g.drawImage(guiImage.get("infobox_bottom")[0], x, infoboxCenterY + (16 * counter), null);
			} catch(IndexOutOfBoundsException ignored) { }
			
//			if (itemList.size() > 2) {
			try {
				drawResourceLine(typoX, y + 45, itemPointer + 1, g);
			} catch(IndexOutOfBoundsException e) {
				drawResourceLine(typoX, y + 45, 0, g);
			}
			
			try {
				drawResourceLine(typoX, y + 145, itemPointer - 1, g);
			} catch (IndexOutOfBoundsException e) {
				drawResourceLine(typoX, y + 145, inventoryService.getItemCount() - 1, g);
			}
//			}
			
		}
		
		g.drawImage(guiImage.get("selection")[0], x, y + 50, null);
		g.drawImage(guiImage.get("arrow_up")[0], arrowX, y + 45, null);
		g.drawImage(guiImage.get("arrow_down")[0], arrowX, y + 95, null);
	}

	private Resource drawResourceLine(final int x, final int y, final int key, final Graphics g) {
		final Resource resource = inventoryService.getResource(key);
		final String str = GamePanel.player.getBackpack().get(resource.getType())
				+ "x " + translator.translate(resource.getName());
		
		g.drawString(str, x, y);
		
		return resource;
	}

	public InterfaceRenderInventoryService getInventoryService() {
		return inventoryService;
	}

	public Inventory setInventoryService(final InterfaceRenderInventoryService inventoryService) {
		this.inventoryService = inventoryService;

		return this;
	}
}