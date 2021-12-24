package core.game.playground;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Date: 22.12.12
 * Time: 10:55
 */
public class PositionMapper {

    private static final PositionMapper instance = new PositionMapper();

    private Rectangle2D.Double[][] area;

    /**
     * The current default size of a sprite in the game
     */
    public static final int SIZE = 50;

    public static PositionMapper getInstance() {
        return PositionMapper.instance;
    }

    private PositionMapper() { }

    public void init(final int maxX, final int maxY) {
        area = new Rectangle2D.Double[maxX][maxY];

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                final Rectangle2D.Double field = new Rectangle2D.Double();
                field.setRect(SIZE * x, SIZE * y, SIZE, SIZE);
                this.area[x][y] = field;
            }
        }
    }

    public Rectangle2D.Double getSizeByPoint(final Point position) {
        return area[position.x][position.y];
    }
}
