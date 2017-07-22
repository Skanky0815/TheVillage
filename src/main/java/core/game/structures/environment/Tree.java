package core.game.structures.environment;

import java.awt.Graphics;
import java.awt.Point;

import core.game.item.ResourcesType;
import core.game.structures.Blueprint;
import core.game.structures.Blueprint.*;
import core.helper.ImageLoader;
import core.helper.Translator;

public class Tree extends Resource implements Reclaimable {

	public Tree(final Point position) {
		super(position, ResourcesType.WOOD, 5);
		this.setPics(ImageLoader.getTreeImage());
	}

	@Override
	public void draw(final Graphics g) {
        if (hasPics) {
            g.drawImage(pics[0], (int) x, (int) (y - 65), null);
        }
        super.draw(g);
    }

	public void reclaim() {
		this.setCurrentLife(1);
	}
	
	public static Blueprint getBlueprint() {
		final Blueprint blueprint = new Blueprint();
        final BuildingType type = BuildingType.TREE;

		blueprint.setType(type);
		blueprint.setIcon(ImageLoader.getIcon(type));
		blueprint.setName(Translator.translate("buildings.tree.name"));
		blueprint.setDescription(Translator.translate("buildings.tree.description"));
		
		blueprint.addToPriceList(ResourcesType.GOLD, 5);
		
		return blueprint;
	}
}
