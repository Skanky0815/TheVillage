package core.game.unit.actions;

import core.game.structures.Structure;
import core.game.unit.NonPlayerCharacter;

public interface Doable {

    public void doing(final NonPlayerCharacter npc);

    public boolean doCollide(final Structure structure);
}
