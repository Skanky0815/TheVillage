package core.game.playground;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.Map;

import core.engine.GameObject;
import core.engine.Sprite;
import core.game.unit.NonPlayerCharacter;
import core.game.unit.Player;
import core.helper.GuiDebugger;
import core.helper.ImageLoader;

public final class Cell extends GameObject {

    private static final int MAX_MOVE_OVER = 5; // TODO change for balance

	private CellType type;

	private final Map<CellType, BufferedImage[]> pics;

	private boolean canGo = true;

    private boolean hasUnit = false;

	private boolean hasStructure = false;

	private int moveOverCounter = 0;

	public Cell(final Point position, final CellType type) {
		super(position);
		this.type = type;

        pics = ImageLoader.getCellImages();
	}

	public CellType getType() {
		return type;
	}

	public void setCellTyp(final CellType cellType) {
		this.type = cellType;
	}

	public boolean hasStructure() {
		return hasStructure;
	}

    public void setHasStructure(final boolean hasStructure) {
        this.hasStructure = hasStructure;
    }
	
	public boolean canGo() {
		return canGo;
	}

    public void setCanGo(final boolean canGo) {
        this.canGo = canGo;
    }

    public boolean hasUnit() {
        return hasUnit;
    }

    @Override
    public void doLogic(final long delta) {
        if ((moveOverCounter == MAX_MOVE_OVER) && !hasStructure) {
            moveOverCounter = 0;
            switchCellTyp();
        }

        if (!hasStructure) {
            type = CellType.GRASS;
        }
    }

    @Override
	public boolean collideWith(final Sprite sprite) {
        boolean isCollided = sprite instanceof NonPlayerCharacter && contains(sprite.getCenterPoint());

        if (sprite instanceof Player && contains(sprite.getCenterPoint())) {
            sprite.setPosition(getPosition());
            isCollided = true;
        }

        if (!isCollided) {
            leavesCell();
        }

		return isCollided;
	}

	public void entersCell() {
        if (!hasUnit) {
            moveOverCounter++;
            canGo = false;
        }
	}

	private void leavesCell() {
        if (hasUnit) {
            hasUnit = false;
        }
	}

	private void switchCellTyp() {
        switch (type) {
            case GRASS -> {
                type = CellType.PATH;
                hasStructure = true;
            }
            case PATH -> {
                type = CellType.STREET;
                hasStructure = true;
            }
            default -> {
            }
        }
	}

	@Override
	public String toString() {
		return String.format(
                "%s@[typ=%s,position=%s/%s]]",
                getClass().getName(),
                type,
                getPosition().x,
                getPosition().y
        );
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(pics.get(type)[0], (int) x, (int) (y - 10), null);

        if (GuiDebugger.isDebugModeOn()) {
            g.setColor(Color.CYAN);
            g.drawString(String.format("%s/%s", getPosition().x, getPosition().y), (int) x + 5, (int) y + 15);
        }
	}
}
