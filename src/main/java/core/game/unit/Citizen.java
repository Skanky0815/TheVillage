package core.game.unit;

import java.awt.Point;

import core.game.dialog.CitizenDialog;
import core.game.playground.MapBuilder;
import core.game.structures.buildings.House;
import core.helper.ImageLoader;
import core.helper.Translator;

public class Citizen extends NonPlayerCharacter  {

	public Citizen(final Point position, final House home) {
		super(position, home);
        setUnitPics(ImageLoader.getCitizenImages());

        name = Translator.translate("citizen.name.male." + (int) (Math.random() * 5));
        speakable = new CitizenDialog();
	}

	@Override
	protected void calculateCellBoni() {
		switch (MapBuilder.getInstance().getCellByPoint(getPosition()).getType()) {
            case FIELD -> calculateSpeed(0.9);
            case GRASS, HILL -> calculateSpeed(1);
            case STREET -> calculateSpeed(2);
            case WOOD -> calculateSpeed(0.5);
            case PATH -> calculateSpeed(1.5);
		}
	}
}