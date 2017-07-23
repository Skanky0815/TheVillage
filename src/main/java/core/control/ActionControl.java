package core.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.engine.Integrable;
import core.engine.Sprite;
import core.engine.SpriteSet;
import core.game.structures.environment.Collectable;
import core.game.ui.GamePanel;

@Singleton
public final class ActionControl {

	private final GameKeyListener keyListener;

	@Inject
	private ActionControl(final GameKeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public boolean doAction() {
		if (keyListener.isSpace()) {
            for (final Sprite actor : SpriteSet.getInstance().getActors()) {
                if (this.isCollisionPointInActionArea(actor)) {
                    return true;
                }
            }
		}
		return false;
	}

	private boolean isCollisionPointInActionArea(final Sprite sprite) {
		if (GamePanel.player.getActionArea().intersects(sprite.getCollisionBox())) {
			if (sprite instanceof Collectable) {
				this.doActionCollected((Collectable) sprite);
				return true;
			}
			
			if (sprite instanceof Integrable) {
				this.interact((Integrable) sprite);
				return true;
			}
		}
		return false;
	}

	private void doActionCollected(final Collectable collectable) {
        GamePanel.player.addResource(collectable.collect());
		keyListener.setSpace(false);
	}
	
	private void interact(final Integrable integrable) {
		integrable.interact(GamePanel.player);
		keyListener.setSpace(false);
	}
}
