package core.game.structures.environment;

import core.game.item.ResourcesType;
import core.helper.ImageLoader;

import java.awt.*;

/**
 * User: RICO
 * Date: 13.01.13
 * Time: 10:59
 */
public class GoldVein extends Resource {

    public GoldVein(final Point position) {
        super(position, ResourcesType.GOLD, 1000);
        this.setPics(ImageLoader.getGoldVeinImage());
    }

    @Override
    public void draw(final Graphics g) {
        if (hasPics) {
            g.drawImage(pics[0], (int) x, (int) y, null);
        }
        super.draw(g);
    }
}
