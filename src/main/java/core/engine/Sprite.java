package core.engine;

import core.game.playground.PositionMapper;

import java.awt.Point;
import java.awt.geom.Rectangle2D;


public abstract class Sprite extends Rectangle2D.Double {

	private Point position;

	protected Sprite(final Point position) {
		this.position = position;

        final PositionMapper positionMapper = PositionMapper.getInstance();
        setRect(positionMapper.getSizeByPoint(position));
        SpriteSet.getInstance().addActor(this);
	}

	public final void setPosition(final Point position) {
		this.position = position;
	}

	public final Point getPosition() {
		return new Point(position);
	}

	public Rectangle2D.Double getCollisionBox() {
		return this;
	}

    public final Point getCenterPoint() {
        return new Point((int) getCenterX(), (int) getCenterY());
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
        return hashCode() == sprite.hashCode();
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s@[position=%s/%s]", getClass().getName(), position.x, position.y);
    }
}
