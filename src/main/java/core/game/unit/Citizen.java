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
        this.setUnitPics(ImageLoader.getCitizenImages());

        name = Translator.translate("citizen.name.male." + (int) (Math.random() * 5));
        speakable = new CitizenDialog();
	}

	@Override
	protected void calculateCellBoni() {
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
}