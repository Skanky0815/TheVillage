package core.game.structures.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import core.engine.Sprite;
import core.game.item.ResourcesType;
import core.game.structures.Structure;
import core.helper.GuiDebugger;

public abstract class Resource extends Structure implements Collectable {

	private final int maxLife;

	private int currentLife;
	
	private final ResourcesType type;
	
	public Resource(final Point position, final ResourcesType type, final int maxLife) {
		super(position);
		this.type = type;
        this.currentLife = this.maxLife = maxLife;
        this.influenceCell(true, true);
	}

	public final void setCurrentLife(final int life) {
		if (currentLife < maxLife) {
            currentLife += life;
		}
	}

    @Override
	public void doLogic(final long delta) {
		super.doLogic(delta);

		if (currentLife == 0) {
			this.remove();
		}
	}

	@Override
	public boolean collideWith(final Sprite s) {
		return false;
	}

	@Override
	public void draw(final Graphics g) {
        if (GuiDebugger.isDebugModeOn()) {
            g.setColor(Color.BLACK);
            g.drawRect((int) (x + 5), (int) (y + 40), (int) (width - 10), 5);


            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) (x + 6), (int) (y + 41), (int) (width - 11), 4);


            g.setColor(Color.GREEN);
            g.fillRect((int) (x + 6), (int) (y + 41), this.calculateLifeBar(), 4);
        }
	}

	@Override
	public ResourcesType collect() {
		if (currentLife != 0) {
			--currentLife;
			return type;
		} else {
			return null;
		}
	}

    private int calculateLifeBar() {
        return (int) (width - 11) * currentLife / maxLife;
    }
}