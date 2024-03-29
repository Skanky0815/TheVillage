package core.game.unit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import core.engine.GameObject;
import core.engine.MoveTo;
import core.engine.Moveable;
import core.game.playground.PositionMapper;
import core.helper.GuiDebugger;

public abstract class Unit extends GameObject implements Moveable {

	private static final double DEFAULT_SPEED = 50.0;

    protected String name = "";

	private double usedSpeed;

	protected double dx;

	protected double dy;

	private Map<String, BufferedImage[]> unitImage;

	protected MoveTo orientation;

	public Unit(final Point position) {
		super(position);
        orientation = MoveTo.S;
	}

	public final String getName() {
		return name;
	}

	protected final void setUnitPics(final Map<String, BufferedImage[]> pics) {
		unitImage = pics;
		hasPics = true;
	}

	public Rectangle2D.Double getActionArea() {
        final int fieldSize = PositionMapper.SIZE;
        final int size = fieldSize / 2;
        final int offset = size / 2;
		return switch (orientation) {
			case N -> new Rectangle2D.Double(x + offset, y - fieldSize + offset, size, size);
		 	case E -> new Rectangle2D.Double(x + fieldSize + offset, y + offset, size, size);
		 	case S -> new Rectangle2D.Double(x + offset, y + fieldSize + offset, size, size);
		 	case W -> new Rectangle2D.Double(x - fieldSize + offset, y + offset, size, size);
		};
	}

	public final MoveTo getOrientation() {
		return orientation;
	}

	protected final void calculateSpeed(final double multiplier) {
		usedSpeed = DEFAULT_SPEED * multiplier;
	}

    @Override
    public void doLogic(final long delta) {
        super.doLogic(delta);
        calculateCellBoni();
    }

    protected abstract void calculateCellBoni();

	public final void moveDown() {
		moveLeftRightStop();
		dy = usedSpeed;
		orientation = MoveTo.S;
	}

	public final void moveRight() {
        moveUpDownStop();
        dx = usedSpeed;
        orientation = MoveTo.E;
	}

	public final void moveLeft() {
        moveUpDownStop();
        dx = -usedSpeed;
        orientation = MoveTo.W;
	}

	public final void moveUp() {
        moveLeftRightStop();
        dy = -usedSpeed;
        orientation = MoveTo.N;
	}

	public final void moveUpDownStop() {
        dy = 0;
	}

	public final void moveLeftRightStop() {
        dx = 0;
	}

	public final void moveStop() {
        moveUpDownStop();
        moveLeftRightStop();
	}

	@Override
	public void move(final long delta) {
		if (dx != 0) {
            x += dx * (delta / 1e9);
		}

		if (dy != 0) {
            y += dy * (delta / 1e9);
		}
	}

    @Override
	public void draw(final Graphics g) {
		if (hasPics) {
			final BufferedImage[] image = unitImage.get(orientation.toString());
			int imageX = (int) x + 9;
			int imageY = (int) y - 15;
			
			int imageIndex = 0;
			if (dy != 0 || dx != 0) {
				imageIndex = currentPic;
			}
			g.drawImage(image[imageIndex], imageX, imageY, null);
		}

		debugGUI(g);
	}
	
	private void debugGUI(final Graphics g) {
		if (GuiDebugger.isDebugModeOn()) {
			final Rectangle2D.Double actionArea = getActionArea();

			g.setColor(Color.CYAN);
			g.drawRect(
					(int) actionArea.getX(), 
					(int) actionArea.getY(), 
					(int) actionArea.getWidth(),
					(int) actionArea.getHeight()
			);

            g.setColor(Color.MAGENTA);
            g.drawRect(
                    (int) x,
                    (int) y,
                    (int) width,
                    (int) height
            );
		}
	}
}