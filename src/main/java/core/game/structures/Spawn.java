package core.game.structures;

import core.game.playground.MapBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public abstract class Spawn extends Structure implements ActionListener {

    private boolean doSpawn = false;

    private final Timer spawnTimer;

    public Spawn(
            final Point position,
            final Rectangle2D.Double rect,
            final MapBuilder mapBuilder,
            final int sizeX,
            final int sizeY
    ) {
        super(position, rect, mapBuilder, sizeX, sizeY);
        spawnTimer = new Timer(2500, this);
        spawnTimer.start();
    }

    @Override
    public void doLogic(final long delta) {
        super.doLogic(delta);

        if (doSpawn) {
            this.doSpawn();
            doSpawn = false;
        }
    }

    public void stopSpawnTimer() {
        spawnTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(spawnTimer)) {
            doSpawn = true;
        }
    }

    protected abstract void doSpawn();
}
