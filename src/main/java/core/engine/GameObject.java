package core.engine;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class GameObject extends Sprite implements Drawable {

    private long animation = 0;

	protected BufferedImage[] pics;

	protected int currentPic = 0;

	protected boolean hasPics = false;

	protected boolean remove;

	protected GameObject(final Point position) {
		super(position);
	}

	protected final void setPics(final BufferedImage[] pics) {
		this.pics = pics;
		hasPics = true;
	}
	
	protected void remove() {
		remove = true;
	}

    public boolean isRemove() {
        return remove;
    }

	public void doLogic(final long delta) {
		animation += delta / 1000000;
        long delay = 175;
        if (animation > delay) {
			animation = 0;
			this.computeAnimation();
		}

        if (remove) {
            SpriteSet.getInstance().removeActor(this);
        }
	}

	private void computeAnimation() {
		currentPic++;
		if (currentPic == 4) {
			currentPic = 0;
		}
	}
}
