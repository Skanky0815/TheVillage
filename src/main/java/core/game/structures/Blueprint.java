package core.game.structures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import core.game.item.ResourcesType;
import core.game.playground.Cell;
import core.game.playground.CellType;
import core.game.playground.MapBuilder;
import core.game.structures.buildings.ResourceSign;
import core.game.structures.buildings.House;
import core.game.structures.buildings.Warehouse;
import core.game.structures.environment.Tree;

public class Blueprint {

    public enum BuildingType {
        TREE, GRAIN, HOUSE, WOOD_SIGN, WAREHOUSE, GOLDVEIN_SIGN
    }

	private String name = "";

	private String description = "";

	private BufferedImage[] icon = null;

	private Map<ResourcesType, Integer> priceList;

	private BuildingType type = null;
	
	public Blueprint() {
        priceList = new HashMap<>();
	}

	public void setType(final BuildingType type) {
		this.type = type;
	}

	public BuildingType getTyp() {
		return this.type;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setPriceList(final Map<ResourcesType, Integer> priceList) {
		this.priceList = priceList;
	}
	
	public void addToPriceList(final ResourcesType key, final int value) {
        priceList.put(key, value);
	}
	
	public Map<ResourcesType, Integer> getPriceList() {
		return priceList;
	}

	public void setIcon(final BufferedImage[] icon) {
		this.icon = icon;
	}
	
	public BufferedImage[] getIcon() {
		return icon;
	}
	
	public void createBuilding(final Point position) {
		switch (type) {
			case TREE -> {
				new Tree(position);
				final Cell cell = MapBuilder.getInstance().getCellByPoint(position);
				cell.setCellTyp(CellType.WOOD);
			}
			case HOUSE -> new House(position);
			case GOLDVEIN_SIGN, WOOD_SIGN -> new ResourceSign(position, type);
			case WAREHOUSE -> new Warehouse(position);
		}
	}
}
