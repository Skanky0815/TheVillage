package core.engine;

import core.game.playground.PositionMapper;

import java.awt.Point;
import java.awt.geom.Rectangle2D;


public abstract class Sprite extends Rectangle2D.Double {

	/**
	 * Hold the current coordinate from the spirit at the map
	 */
	private Point position;

    /**
     * The default constructor for a sprite
     * @param position Point
     */
	protected Sprite(final Point position) {
		this.position = position;

        final PositionMapper positionMapper = PositionMapper.getInstance();
        this.setRect(positionMapper.getSizeByPoint(position));
        SpriteSet.getInstance().addActor(this);
	}

	/**
	 * Set the new Position after move by collide with a cell
	 * @param position Point
	 */
	public final void setPosition(final Point position) {
		this.position = position;
	}

	/**
	 * Get the Positionpoint from the Sprite. This represents the X and Y
	 * coordinate form the MapBuilder
	 * @return Point
	 */
	public final Point getPosition() {
		return new Point(position);
	}

	/**
	 * Get the collision box from the Sprite for
	 * <code>collideWith(Sprite s)</code>. The box is
	 * in the center of the Sprite an has a size from
	 * 20px x 20px.
	 * @return Rectangle2D.Double
	 */
	public Rectangle2D.Double getCollisionBox() {
		return this;
	}

    /**
     * Return the a central point
     * @return Point
     */
    public final Point getCenterPoint() {
        return new Point((int) this.getCenterX(), (int) this.getCenterY());
    }

	public abstract boolean collideWith(final Sprite s);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Sprite)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        final Sprite sprite = (Sprite) o;
        return this.hashCode() == sprite.hashCode();
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s@[position=%s/%s]", this.getClass().getName(), position.x, position.y);
    }
}
