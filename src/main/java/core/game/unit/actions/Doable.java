package core.game.unit.actions;

import core.game.structures.Structure;
import core.game.unit.NonPlayerCharacter;

public interface Doable {

    void doing(final NonPlayerCharacter npc);

    boolean doCollide(final Structure structure);
}
