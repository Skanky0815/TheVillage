package core.game.unit.actions;

import core.game.structures.Structure;
import core.game.unit.NonPlayerCharacter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoNothing implements Doable, ActionListener {

    private final Timer doTimer = new Timer(2000, this);

    private NonPlayerCharacter npc;

    @Override
    public void doing(final NonPlayerCharacter npc) {
        this.npc = npc;

        if (!npc.isMoving() && !doTimer.isRunning()) {
            doTimer.start();
        }
    }

    @Override
    public boolean doCollide(final Structure structure) {
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(doTimer)) {
            npc.setRandomOrientation();
            doTimer.stop();
        }
    }
}
