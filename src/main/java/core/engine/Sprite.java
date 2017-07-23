package core.engine;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 * The Sprite class is the basic class for all elements with are showed at the game frame
 */
public abstract class Sprite extends Rectangle2D.Double {

	/**
	 * Hold the current coordinate from the spirit at the map
	 */
	private Point position;

    /**
     * The default constructor for a sprite
	 *
     * @param position Point
     */
	protected Sprite(final Point position, final Rectangle2D.Double rect) {
		this.position = position;
        this.setRect(rect);
	}

	/**
	 * Set the new Position after move by collide with a cell
	 *
	 * @param position Point
	 */
	public final void setPosition(final Point position) {
		this.position = position;
	}

	/**
	 * Get the Positionpoint from the Sprite. This represents the X and Y
	 * coordinate form the MapBuilder
	 *
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
	 *
	 * @return Rectangle2D.Double
	 */
	public Rectangle2D.Double getCollisionBox() {
		return this;
	}

    /**
     * Return the a central point
	 *
     * @return Point
     */
    public final Point getCenterPoint() {
        return new Point((int) getCenterX(), (int) getCenterY());
    }

	public abstract boolean collideWith(final Sprite s);

	/**
	 * Check if the object it equals with the given object
	 *
	 * @param o The object to check
	 *
	 * @return true is the given object is equals with this object
	 */
	@Override
    public boolean equals(final Object o) {
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

	/**
	 * Generate a custom hash code
	 *
	 * @return The Hash
	 */
	@Override
    public int hashCode() {
        return 31 * super.hashCode() + getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s@[position=%s/%s]", getClass().getName(), position.x, position.y);
    }
}
