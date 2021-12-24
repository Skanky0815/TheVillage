package core.game.structures.buildings;

import java.awt.Graphics;
import java.awt.Point;

import core.engine.Integrable;
import core.engine.Sprite;
import core.game.playground.MapBuilder;
import core.game.item.ResourcesType;
import core.game.structures.Spawn;
import core.game.structures.Blueprint;
import core.game.structures.Blueprint.*;
import core.game.unit.Citizen;
import core.game.unit.Player;
import core.game.unit.actions.DoGoHome;
import core.helper.ImageLoader;
import core.helper.Translator;

public class House extends Spawn implements Integrable {

	private boolean isInteract = false;

	private final int maxResidents;

	private int currentNumberOfResidents;

	public House(final Point position) {
		super(position, 2, 2);
        maxResidents = 1; // TODO change
        currentNumberOfResidents = 0;
        this.setPics(ImageLoader.getBuildingImage(BuildingType.HOUSE));
        this.influenceCell(false, true);
	}

    @Override
    protected void doSpawn() {
        if (currentNumberOfResidents < maxResidents) {
            final Citizen citizen = new Citizen(MapBuilder.getInstance().getDefaultSpawnPoint(), this);
            citizen.setDoable(new DoGoHome());
            currentNumberOfResidents++;
        } else {
            this.stopSpawnTimer();
        }
    }

    @Override
	public void interact(final Player player) {
        isInteract = true;
	}

	@Override
	public boolean collideWith(final Sprite s) {
		return false;
	}

	@Override
	public void draw(final Graphics g) {
        if (hasPics) {
		    g.drawImage(pics[0], (int) x, (int) y, null);
        }

		if (isInteract) {
			// TODO dialog for the house
            isInteract = false;
		}
	}

	public static Blueprint getBlueprint() {
		final Blueprint blueprint = new Blueprint();
		final BuildingType type = BuildingType.HOUSE;

		blueprint.setType(type);
		blueprint.setIcon(ImageLoader.getIcon(type));
		blueprint.setName(Translator.translate("buildings.house.name"));
        blueprint.setDescription(Translator.translate("buildings.house.description"));
		
		blueprint.addToPriceList(ResourcesType.GOLD, 5);
		blueprint.addToPriceList(ResourcesType.WOOD, 5);
		
		return blueprint;
	}
}
