package core.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import core.TheVillage;
import core.engine.MoveTo;
import core.game.item.ResourcesType;
import core.game.playground.CellType;
import core.game.structures.Blueprint.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageLoader {

    private static final Logger LOGGER = LogManager.getLogger(ImageLoader.class);

    private static final String ROOT_IMAGE_PATH = TheVillage.BASE_RESOURCES_PATH + "image" + File.separator;

    private static Map<BuildingType, BufferedImage[]> buildingIcons;

    private static Map<ResourcesType, BufferedImage[]> rescourcesIcons;

    private static Map<CellType, BufferedImage[]> cellImage;

    private static Map<String, BufferedImage[]> playerImage;

    private static List<Map<String, BufferedImage[]>> citizenImages;

    private static Map<String, BufferedImage[]> guiBuildMenu;

    private static Map<String, BufferedImage[]> guiInventory;

	private static Map<String, BufferedImage[]> guiPlayerBar;

	private static Map<String, BufferedImage[]> defaultGUIElements;

	private static Map<BuildingType, BufferedImage[]> buldings;

	private static BufferedImage[] treeImage;

    private static BufferedImage[] goldVeinImage;

	private ImageLoader() { }
	
	public static void init() {
		ImageLoader.loadIcons();
        ImageLoader.loadPlayerImage();
        ImageLoader.loadCitizenImage();
        ImageLoader.loadCellImage();
        ImageLoader.loadGUI();
        ImageLoader.loadBuildings();
		
		treeImage = ImageLoader.loadImage(ROOT_IMAGE_PATH + "terrain" + File.separator + "tree.png", 1);
        goldVeinImage = ImageLoader.loadImage(ROOT_IMAGE_PATH + "terrain" + File.separator + "goldvein.png", 1);
        LOGGER.info("All images are loaded");
	}
	
	private static void loadBuildings() {
		final String path = ROOT_IMAGE_PATH + "buildings" + File.separator;

		buldings = new HashMap<BuildingType, BufferedImage[]>();
		buldings.put(BuildingType.WOOD_SIGN,     ImageLoader.loadImage(path + "wood_sign.png", 1));
        buldings.put(BuildingType.GOLDVEIN_SIGN, ImageLoader.loadImage(path + "goldvein_sign.png", 1));
		buldings.put(BuildingType.HOUSE,         ImageLoader.loadImage(path + "house.png", 1));
		buldings.put(BuildingType.WAREHOUSE,     ImageLoader.loadImage(path + "warehouse.png", 1));
	}
	
	private static void loadCitizenImage() {
		final String path = ROOT_IMAGE_PATH + "citizen" + File.separator;
		
		citizenImages = new ArrayList<Map<String, BufferedImage[]>>(); 
		final Map<String, BufferedImage[]> citizen1 = new HashMap<String, BufferedImage[]>();
		citizen1.put(MoveTo.S.toString(), ImageLoader.loadImage(path + "citizen1_WalkDown.png", 4));
		citizen1.put(MoveTo.W.toString(), ImageLoader.loadImage(path + "citizen1_WalkLeft.png", 4));
		citizen1.put(MoveTo.E.toString(), ImageLoader.loadImage(path + "citizen1_WalkRight.png", 4));
		citizen1.put(MoveTo.N.toString(), ImageLoader.loadImage(path + "citizen1_WalkUp.png", 4));
		citizenImages.add(citizen1);
		
		final Map<String, BufferedImage[]> citizen2 = new HashMap<String, BufferedImage[]>();
		citizen2.put(MoveTo.S.toString(), ImageLoader.loadImage(path + "citizen2_WalkDown.png", 4));
		citizen2.put(MoveTo.W.toString(), ImageLoader.loadImage(path + "citizen2_WalkLeft.png", 4));
		citizen2.put(MoveTo.E.toString(), ImageLoader.loadImage(path + "citizen2_WalkRight.png", 4));
		citizen2.put(MoveTo.N.toString(), ImageLoader.loadImage(path + "citizen2_WalkUp.png", 4));
		citizenImages.add(citizen2);
	}

	private static void loadGUI() {
		final String path = ROOT_IMAGE_PATH + "gui" + File.separator;
		
		defaultGUIElements = new HashMap<String, BufferedImage[]>();
		defaultGUIElements.put("arrow_up",      ImageLoader.loadImage(path + "arrow_up.png", 1));
		defaultGUIElements.put("arrow_down",    ImageLoader.loadImage(path + "arrow_down.png", 1));
		defaultGUIElements.put("arrow_left",    ImageLoader.loadImage(path + "arrow_left.png", 1));
		defaultGUIElements.put("arrow_right",   ImageLoader.loadImage(path + "arrow_right.png", 1));
		
		ImageLoader.loadBuildMenu(path);
        ImageLoader.loadInventory(path);
        ImageLoader.playerBar(path);
	}
	
	private static void playerBar(String path) {
		path += "playerbar" + File.separator;
		
		guiPlayerBar = new HashMap<String, BufferedImage[]>();
		guiPlayerBar.putAll(defaultGUIElements);
		guiPlayerBar.put("background", ImageLoader.loadImage(path + "background.png", 1));
	}

	private static void loadInventory(String path) {
		path += "inventory" + File.separator;
		
		guiInventory = new HashMap<String, BufferedImage[]>();
		guiInventory.putAll(defaultGUIElements);
		guiInventory.put("selection",       ImageLoader.loadImage(path + "selection.png", 1));
		guiInventory.put("list",            ImageLoader.loadImage(path + "list.png", 1));
		guiInventory.put("infobox_top",     ImageLoader.loadImage(path + "infobox_top.png", 1));
		guiInventory.put("infobox_center",  ImageLoader.loadImage(path + "infobox_center.png", 1));
		guiInventory.put("infobox_bottom",  ImageLoader.loadImage(path + "infobox_bottom.png", 1));
	}

	private static void loadBuildMenu(String path) {
		path += "buildmenu" + File.separator;
		
		guiBuildMenu = new HashMap<String, BufferedImage[]>();
		guiBuildMenu.putAll(defaultGUIElements);
		guiBuildMenu.put("selection",       ImageLoader.loadImage(path + "selection.png", 1));
		guiBuildMenu.put("background",      ImageLoader.loadImage(path + "background.png", 1));
		guiBuildMenu.put("infobox_top",     ImageLoader.loadImage(path + "infobox_top.png", 1));
		guiBuildMenu.put("infobox_center",  ImageLoader.loadImage(path + "infobox_center.png", 1));
		guiBuildMenu.put("infobox_bottom",  ImageLoader.loadImage(path + "infobox_bottom.png", 1));
	}
	
	
	private static void loadCellImage() {
		final String path = ROOT_IMAGE_PATH + "cells" + File.separator;
		
		cellImage = new HashMap<CellType, BufferedImage[]>();
		cellImage.put(CellType.GRASS,   ImageLoader.loadImage(path + "grass.png", 1));
		cellImage.put(CellType.STREET,  ImageLoader.loadImage(path + "street.png", 1));
		cellImage.put(CellType.PATH,    ImageLoader.loadImage(path + "path.png", 1));
		cellImage.put(CellType.WOOD,    ImageLoader.loadImage(path + "wood.png", 1));
	}

	private static void loadIcons() {
		final String path = ROOT_IMAGE_PATH + "icons" + File.separator;

        ImageLoader.loadBuildingIcons(path);
        ImageLoader.loadRescourcesIcons(path);
	}
	
	private static void loadRescourcesIcons(String path) {
		path += "rescources" + File.separator;
		
		rescourcesIcons = new HashMap<ResourcesType, BufferedImage[]>();
		rescourcesIcons.put(ResourcesType.WOOD, ImageLoader.loadImage(path + "s_wood.png", 1));
		rescourcesIcons.put(ResourcesType.GOLD, ImageLoader.loadImage(path + "s_gold.png", 1));
	}

	private static void loadBuildingIcons(String path) {
		path += "building" + File.separator;

		buildingIcons = new HashMap<BuildingType, BufferedImage[]>();
		buildingIcons.put(BuildingType.WAREHOUSE,    ImageLoader.loadImage(path + "icon_.png", 2));
		buildingIcons.put(BuildingType.TREE,         ImageLoader.loadImage(path + "icon_tree.png", 2));
		buildingIcons.put(BuildingType.GRAIN,        ImageLoader.loadImage(path + "icon_grain.png", 2));
		buildingIcons.put(BuildingType.HOUSE,        ImageLoader.loadImage(path + "icon_house.png", 2));
        buildingIcons.put(BuildingType.WOOD_SIGN,    ImageLoader.loadImage(path + "icon_wood_sign.png" , 2));
        buildingIcons.put(BuildingType.GOLDVEIN_SIGN,ImageLoader.loadImage(path + "icon_goldvein_sign.png" , 2));
	}

	private static void loadPlayerImage() {
		final String path = ROOT_IMAGE_PATH + "player" + File.separator;
		
		playerImage = new HashMap<String, BufferedImage[]>();
		playerImage.put(MoveTo.S.toString(), ImageLoader.loadImage(path + "player_WalkDown.png", 4));
		playerImage.put(MoveTo.W.toString(), ImageLoader.loadImage(path + "player_WalkLeft.png", 4));
		playerImage.put(MoveTo.E.toString(), ImageLoader.loadImage(path + "player_WalkRight.png", 4));
		playerImage.put(MoveTo.N.toString(), ImageLoader.loadImage(path + "player_WalkUp.png", 4));
	}

	private static BufferedImage[] loadImage(final String path, final int pics) {
        BufferedImage source = null;
        final File file = new File(path);
		try {
			source = ImageIO.read(file);
		} catch (IOException ignored) {
			LOGGER.error("File not found: " + file);
		}
		return ImageLoader.splittedUpImage(source, pics);
	}

    private static BufferedImage[] splittedUpImage(final BufferedImage image, final int numberOfPics) {
        final BufferedImage[] animations = new BufferedImage[numberOfPics];
        try{
            for (int i = 0; i < numberOfPics; i++) {
                animations[i] = image.getSubimage(
                        i * image.getWidth() / numberOfPics,
                        0,
                        image.getWidth() / numberOfPics,
                        image.getHeight()
                );
            }
        } catch (final NullPointerException ignored) {
            LOGGER.error("File could not be splitted in to there parts");
        }

        return animations;
    }

	public static BufferedImage[] getIcon(final BuildingType key) {
		return buildingIcons.get(key);
	}

	public static Map<String, BufferedImage[]> getPlayerImages() {
		return playerImage;
	}

	public static Map<CellType, BufferedImage[]> getCellImages() {
		return cellImage;
	}

	public static Map<String, BufferedImage[]> getBuildMenuGUI() {
		return guiBuildMenu;
	}

	public static Map<String, BufferedImage[]> getInventoryGUI() {
		return guiInventory;
	}

	public static BufferedImage getRescourcesIcons(final ResourcesType key) {
		return rescourcesIcons.get(key)[0];
	}

	public static Map<String, BufferedImage[]> getPlayerBarGUI() {
		return guiPlayerBar;
	}

	public static Map<String, BufferedImage[]> getCitizenImages() {
		final int key = (int) (Math.random() * citizenImages.size());
		return citizenImages.get(key);
	}

	public static BufferedImage[] getBuildingImage(final BuildingType type) {
		return buldings.get(type);
	}

    public static BufferedImage[] getTreeImage() {
        return treeImage;
    }

    public static BufferedImage[] getGoldVeinImage() {
        return goldVeinImage;
    }
}
