package core.engine.services;

import com.google.inject.Inject;
import core.engine.GameObject;
import core.engine.SpriteSet;

import java.awt.*;

public abstract class GameObjectFactory<T extends GameObject> {

    /**
     * The SpriteSet object
     */
    private final SpriteSet spriteSet;

    @Inject
    public GameObjectFactory(final SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    /**
     * Create the new GameObject, store them in the SpiritSet an return them.
     *
     * @param position The spawn position of the new GameObject
     *
     * @return the new created GameObject
     */
    public T create(final Point position) {
        final T gameObject = createHook(position);
        spriteSet.addActor(gameObject);

        return gameObject;
    }

    /**
     * This method must be override to create the specialized GameObject
     *
     * @param position The spawn position of the new GameObject
     *
     * @return the new created GameObject
     */
    protected abstract T createHook(final Point position);
}
