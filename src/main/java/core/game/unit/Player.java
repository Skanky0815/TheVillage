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

	private final Map<ResourcesType, Integer> backpack;

	private final List<ArrayList<Blueprint>> buildableList;

	private int buildingTyp;

	private int building;

	public Player(final Point position) {
		super(position);

        orientation = MoveTo.S;
        backpack = new HashMap<>();
        backpack.put(ResourcesType.GOLD, 20);
        backpack.put(ResourcesType.WOOD, 40);
        backpack.put(ResourcesType.GRAIN, 10);

        buildableList = GamePanel.buildableList;

        setUnitPics(ImageLoader.getPlayerImages());
	}

    @Override
    public boolean collideWith(final Sprite s) {
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
		if (!MapBuilder.getInstance().getCellByPoint(getPosition()).hasStructure()) {
			final var blueprint = buildableList.get(buildingTyp).get(building);

			if (checkConstructable(blueprint)) {
				blueprint.createBuilding(getPosition());
                calculateInventoryResources(blueprint);
			}
		} else {
            // TODO implement a user feedback
        }
	}

	private void calculateInventoryResources(final Blueprint blueprint) {
		for (final Map.Entry<ResourcesType, Integer> price : blueprint.getPriceList().entrySet()) {
			final var type = price.getKey();
			if (backpack.containsKey(type)) {
				final var value = backpack.get(type) - price.getValue();
                backpack.put(type, value);

				if (backpack.get(type) <= 0) {
                    backpack.remove(type);
				}
			}
		}
	}

	private boolean checkConstructable(final Blueprint blueprint) {
		for (final Map.Entry<ResourcesType, Integer> price : blueprint.getPriceList().entrySet()) {
			if (!canBuild(price.getKey(), price.getValue())) {
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
		switch (MapBuilder.getInstance().getCellByPoint(getPosition()).getType()) {
            case FIELD:
                calculateSpeed(0.9);
                break;
            case GRASS:
            case HILL:
                calculateSpeed(1);
                break;
            case RIVER:
                break;
            case STREET:
                calculateSpeed(2);
                break;
            case WOOD:
                calculateSpeed(0.5);
                break;
            case PATH:
                calculateSpeed(1.5);
                break;
		}
	}

    @Override
	public String toString() {
		final var str = new StringBuilder(getClass().getName() + "@[");

		str.append("Backpack=");
		for (final Map.Entry<ResourcesType, Integer> e : backpack.entrySet()) {
			str.append(e.getKey()).append("::").append(e.getValue()).append(",");
		}
		str.append(",position=").append(getPosition().x).append("|").append(getPosition().y).append("]");

		return str.toString();
	}
}