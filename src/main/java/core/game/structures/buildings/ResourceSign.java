package core.game.structures.buildings;

import java.awt.Graphics;
import java.awt.Point;

import core.engine.Integrable;
import core.engine.Sprite;
import core.game.item.ResourcesType;
import core.game.structures.Structure;
import core.game.structures.environment.GoldVein;
import core.game.structures.environment.Tree;
import core.game.structures.Blueprint;
import core.game.structures.Blueprint.*;
import core.game.unit.Player;
import core.helper.ImageLoader;
import core.helper.Translator;

public class ResourceSign extends Structure implements Integrable {

	private final BuildingType singType;
	
	private Class resource;
	
	public ResourceSign(final Point position, final BuildingType singType) {
		super(position);
		this.singType = singType;
        this.setPics(ImageLoader.getBuildingImage(singType));

		switch(singType) {
            case WOOD_SIGN:
                resource = Tree.class;
                break;
            case GOLDVEIN_SIGN:
                resource = GoldVein.class;
                break;
            default:
                // TODO fill other sings pics there
                break;
		}

        this.influenceCell(false, true);
	}

	public final Class getResource() {
		return resource;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(pics[0], (int) x, (int) y - 15, null);
	}

	@Override
	public void interact(final Player player) {
		this.remove();
	}

	@Override
	public boolean collideWith(final Sprite s) {
		return false;
	}
	
	public static Blueprint getBlueprint(final ResourcesType type) {
		return switch (type) {
			case WOOD -> ResourceSign.getWoodsignBlueprint();
			case GOLD -> ResourceSign.getGoldveinSignBlueprint();
			default -> null;
		};
	}
	
	private static Blueprint getWoodsignBlueprint() {
        final Blueprint blueprint = new Blueprint();
		final BuildingType buildingType = BuildingType.WOOD_SIGN;
		blueprint.setType(buildingType);
		blueprint.setIcon(ImageLoader.getIcon(buildingType));
		blueprint.setName(Translator.translate("buildings.woodSing.name"));
		blueprint.setDescription(Translator.translate("buildings.woodSing.description"));
		
		blueprint.addToPriceList(ResourcesType.WOOD, 3);

        return blueprint;
	}

    private static Blueprint getGoldveinSignBlueprint() {
        final Blueprint blueprint = new Blueprint();
        final BuildingType buildingType = BuildingType.GOLDVEIN_SIGN;
        blueprint.setType(buildingType);
        blueprint.setIcon(ImageLoader.getIcon(buildingType));
        blueprint.setName(Translator.translate("buildings.goldVeinSing.name"));
        blueprint.setDescription(Translator.translate("buildings.goldVeinSing.description"));

        blueprint.addToPriceList(ResourcesType.WOOD, 3);

        return blueprint;
    }
}
