package core.game.playground;

import core.game.structures.environment.Tree;

import java.awt.*;

/**
 * User: RICO
 * Date: 27.12.12
 * Time: 12:07
 */
public class CellFactory {

    public static Cell getCell(final Point position, final CellType type) {
        final Cell cell = new Cell(position, type);
        switch (type) {
            case WOOD:
                new Tree(position);
                break;
            case STREET:
                cell.setHasStructure(true);
            default:
                break;
        }

        return cell;
    }
}
