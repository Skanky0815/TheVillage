package core.game.structures.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serial;

import core.engine.Sprite;
import core.game.item.ResourcesType;
import core.game.structures.Structure;

public class Grain extends Structure implements Collectable {

	@Serial
	private static final long serialVersionUID = 1L;

	private int grain;
	
	public Grain(final Point position) {
		super(position);

		this.grain = 10;
	}

	@Override
	public ResourcesType collect() {
		if (this.grain != 0) {
			--this.grain;
			return ResourcesType.GRAIN;
		} else {
            remove();
			return null;
		}
	}

	@Override
	public boolean collideWith(final Sprite s) {
		return false;
	}

	@Override
	public void draw(final Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) this.x, (int) this.y, 25, 25);
	}
}
