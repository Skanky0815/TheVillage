package core.game.structures.buildings;

import java.awt.*;
import java.util.*;

import core.engine.Integrable;
import core.engine.Sprite;
import core.game.item.ResourcesType;
import core.game.structures.Structure;
import core.game.structures.Blueprint;
import core.game.structures.Blueprint.*;
import core.game.unit.Player;
import core.helper.ImageLoader;
import core.helper.Translator;

public class Warehouse extends Structure implements Integrable {

    private Map<ResourcesType, Integer> resourcesStore = new HashMap<ResourcesType, Integer>();

	public Warehouse(final Point position) {
		super(position, 2, 2);
        this.setPics(ImageLoader.getBuildingImage(BuildingType.WAREHOUSE));

        for (final ResourcesType resourcesType : ResourcesType.values()) {
            resourcesStore.put(resourcesType, 0);
        }

        this.influenceCell(false, true);
	}

    public void storeResource(final ResourcesType resource) {
        final int number = resourcesStore.get(resource) + 1;
        resourcesStore.put(resource, number);
    }

	@Override
	public void interact(final Player player) {
        LOGGER.debug(Arrays.toString(resourcesStore.values().toArray()));
	}

	@Override
	public boolean collideWith(final Sprite s) {
		return false;
	}

	@Override
	public void draw(final Graphics g) {
		if (hasPics) {
			g.drawImage(pics[0], (int) x, (int) y, null);

            int y = (int) this.y + 10;
            final int x = (int) this.x + 10;
            g.setColor(Color.WHITE);
            for (final Map.Entry<ResourcesType, Integer> entry : resourcesStore.entrySet()) {
                g.drawString(String.format("%s: %sx", entry.getKey(), entry.getValue()), x,  y);
                y += 12;
            }
		}
	}

	public static Blueprint getBlueprint() {
		final Blueprint blueprint = new Blueprint();
		final BuildingType type = BuildingType.WAREHOUSE;

		blueprint.setType(type);
		blueprint.setIcon(ImageLoader.getIcon(type));
		blueprint.setName(Translator.translate("buildings.warehouse.name"));
		blueprint.setDescription(Translator.translate("buildings.warehouse.description"));

		blueprint.addToPriceList(ResourcesType.GOLD, 5);
		blueprint.addToPriceList(ResourcesType.WOOD, 5);

		return blueprint;
	}
}
