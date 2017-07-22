package core.game.structures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Spawn extends Structure implements ActionListener {

    private boolean doSpawn = false;

    private Timer spawnTimer;

    public Spawn(final Point position, final int sizeX, final int sizeY) {
        super(position, sizeX, sizeY);
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
