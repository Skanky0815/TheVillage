package core.game.unit;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.engine.MoveTo;
import core.engine.Sprite;
import core.game.item.ResourcesType;
import core.game.playground.MapBuilder;
import core.game.structures.Blueprint;
import core.game.ui.GamePanel;
import core.helper.ImageLoader;

public class Player extends Unit {

	private Map<ResourcesType, Integer> backpack;

	private List<ArrayList<Blueprint>> buildableList;

	private int buildingTyp;

	private int building;

	public Player(Point position) {
		super(position);

        orientation = MoveTo.S;
        backpack = new HashMap<ResourcesType, Integer>();
        backpack.put(ResourcesType.GOLD, 20);
        backpack.put(ResourcesType.WOOD, 40);

        buildableList = GamePanel.buildableList;

        this.setUnitPics(ImageLoader.getPlayerImages());
	}

    @Override
    public boolean collideWith(Sprite s) {
        return false;
    }

    public void addResource(final ResourcesType resource) {
		try {
			int value = backpack.get(resource);
			value = value + 1;
            backpack.put(resource, value);
		} catch (final NullPointerException e) {
            backpack.put(resource, 1);
		}
	}

	public final Map<ResourcesType, Integer> getBackpack() {
		return backpack;
	}

	public final int getBackpackSize() {
		int counter = 0;
		for (final Map.Entry<ResourcesType, Integer> e : backpack.entrySet()) {
			counter += e.getValue();
		}
		return counter;
	}

	public final void setBuilding(final int building) {
		this.building = building;
	}

	public final void setBuildingTyp(final int buildingTyp) {
		this.buildingTyp = buildingTyp;
	}

	public void build() {
		if (!MapBuilder.getInstance().getCellByPoint(this.getPosition()).hasStructure()) {
			final Blueprint blueprint = buildableList.get(buildingTyp).get(building);

			if (this.checkConstructable(blueprint)) {
				blueprint.createBuilding(this.getPosition());
                this.calculateInventoryResources(blueprint);
			}
		} else {
            // TODO implement a user feedback
            LOGGER.debug("There is a other structure!");
        }
	}

	private void calculateInventoryResources(final Blueprint blueprint) {
		for (final Map.Entry<ResourcesType, Integer> price : blueprint.getPriceList().entrySet()) {
			final ResourcesType type = price.getKey();
			if (backpack.containsKey(type)) {
				final Integer value = backpack.get(type) - price.getValue();
                backpack.put(type, value);

				if (backpack.get(type) <= 0) {
                    backpack.remove(type);
				}
			}
		}
	}

	private boolean checkConstructable(final Blueprint blueprint) {
		for (final Map.Entry<ResourcesType, Integer> price : blueprint.getPriceList().entrySet()) {
			if (!this.canBuild(price.getKey(), price.getValue())) {
				return false;
			}
		}
		return true;
	}

	public boolean canBuild(final ResourcesType type, final Integer price) {
        return !(!backpack.containsKey(type) || price > backpack.get(type));
    }

	@Override
	public void calculateCellBoni() {
		switch (MapBuilder.getInstance().getCellByPoint(this.getPosition()).getType()) {
            case FIELD:
                this.calculateSpeed(0.9);
                break;
            case GRASS:
                this.calculateSpeed(1);
                break;
            case HILL:
                this.calculateSpeed(1);
                break;
            case RIVER:
                break;
            case STREET:
                this.calculateSpeed(2);
                break;
            case WOOD:
                this.calculateSpeed(0.5);
                break;
            case PATH:
                this.calculateSpeed(1.5);
                break;
		}
	}

    @Override
	public String toString() {
		String str = this.getClass().getName() + "@[";

		str += "Backpack=";
		for (final Map.Entry<ResourcesType, Integer> e : backpack.entrySet()) {
			str += e.getKey() + "::" + e.getValue() + ",";
		}
		str += ",position=" + this.getPosition().x + "|" + this.getPosition().y + "]";

		return str;
	}
}