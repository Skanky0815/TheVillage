package core.engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Date: 22.12.12
 * Time: 02:30
 */
public class SpriteSet {

    private final static SpriteSet instance = new SpriteSet();

    private final Vector<Sprite> actors = new Vector<Sprite>(100, 10);

    public static SpriteSet getInstance() {
        return SpriteSet.instance;
    }

    private SpriteSet() { }

    public int actorsSize() {
        return actors.size();
    }

    public Vector<Sprite> getActors() {
        return actors;
    }

    @SuppressWarnings("unchecked")
    public Vector<Sprite> getClonedActors() {
        return (Vector<Sprite>) actors.clone();
    }

    public void removeActor(final Sprite sprite) {
        actors.remove(sprite);
        sort();
    }

    public void addActor(final Sprite sprite) {
        actors.add(sprite);
        sort();
    }

    public <T> List<T> getActorsByClass(final Class<T> ofClass) {
        final List<T> tList = new ArrayList<>();
        for (final var sprite : actors) {
            if (ofClass.isInstance(sprite)) {
                tList.add((T) sprite);
            }
        }
        return tList;
    }

    public <T extends Sprite> T getClosestActor(final Point position, final Class<T> targetClass) {
        T temp = null;
        for (final T sprite : getActorsByClass(targetClass)) {
            if (null == temp) {
                temp = sprite;
                continue;
            }

            final double v1 = calculateValue(position, temp.getPosition());
            final double v2 = calculateValue(position, sprite.getPosition());
            if (v1 > v2) {
                temp = sprite;
            }
        }
        return temp;
    }

    private double calculateValue(final Point start, final Point end) {
        final var vX = end.x - start.x;
        final var vY = end.y - start.y;

        return Math.sqrt((vX * vX) + (vY * vY));
    }

    private void sort() {
        Collections.sort(actors, SpriteXAxisSorter.getInstance());
    }
}
