package core.engine;

import core.game.playground.Cell;
import core.game.unit.Unit;

import java.util.Comparator;

/**
 * User: RICO
 * Date: 31.12.12
 * Time: 00:37
 */
public final class SpriteXAxisSorter implements Comparator<Sprite> {

    private static final SpriteXAxisSorter instance = new SpriteXAxisSorter();

    public static SpriteXAxisSorter getInstance() {
        return instance;
    }

    private SpriteXAxisSorter() { }

    public int compare(Sprite sprite1, Sprite sprite2) {
        if (sprite1 instanceof Unit) {
            return 1;
        }

        if ((sprite1 instanceof Cell) && !(sprite2 instanceof Cell)) {
            return -1;
        }

        if (!(sprite1 instanceof Cell) && (sprite2 instanceof Cell)) {
            return 1;
        }

        return Integer.compare(sprite1.getPosition().x, sprite2.getPosition().x);
    }
}
