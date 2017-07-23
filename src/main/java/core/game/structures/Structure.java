package core.game.structures;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import core.engine.GameObject;
import core.game.playground.Cell;
import core.game.playground.MapBuilder;

public abstract class Structure extends GameObject {

    private final MapBuilder mapBuilder;
    private final int sizeX;
    private final int sizeY;

    private boolean initAtDoLogic;

    private boolean canGo;

    private boolean hasStructures;

    public Structure(final Point position, final Rectangle2D.Double rect, final MapBuilder mapBuilder) {
        this(position, rect, mapBuilder, 1, 1);
    }

	public Structure(
	        final Point position,
            final Rectangle2D.Double rect,
            final MapBuilder mapBuilder,
            final int sizeX,
            final int sizeY
    ) {
		super(position, rect);
        this.mapBuilder = mapBuilder;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
	}

    @Override
    public void doLogic(long delta) {
        super.doLogic(delta);

        if (initAtDoLogic) {
            this.influenceCell(canGo, hasStructures);
        }
    }

    @Override
    protected void remove() {
        super.remove();
        this.influenceCell(true, false);
    }

    @Override
    public Double getCollisionBox() {
        return new Rectangle2D.Double(x , y, width * sizeX, height * sizeY);
    }

    protected final void influenceCell(final boolean canGo, final boolean hasStructures) {
        try {
            final int startPointX = this.getPosition().x;
            final int startPointY = this.getPosition().y;
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    final Cell cell = mapBuilder.getCellByPoint(startPointX + x, startPointY + y);
                    cell.setCanGo(canGo);
                    cell.setHasStructure(hasStructures);
                }
            }
        } catch (final Exception ignored) {
            this.canGo = canGo;
            this.hasStructures = hasStructures;
        }
    }
}
