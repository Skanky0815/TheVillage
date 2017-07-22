package core.game.unit.actions;

import core.game.structures.Structure;
import core.game.unit.NonPlayerCharacter;

public class DoGoHome implements Doable {

    private NonPlayerCharacter npc;

    @Override
    public void doing(final NonPlayerCharacter npc) {
        this.npc = npc;
        npc.setWaypoint(npc.getHome().getPosition());
    }

    @Override
    public boolean doCollide(final Structure structure) {
        if (npc.getActionArea().intersects(structure) && structure.equals(npc.getHome())) {
            npc.setNextDoable(new DoNothing());
            return true;
        }
        npc.setRandomOrientation();
        return false;
    }
}
