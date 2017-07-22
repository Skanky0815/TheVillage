package core.game.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Timer;

import com.google.inject.Injector;

import core.control.*;
import core.engine.*;

import core.engine.graphic.Screen;
import core.engine.services.game.InventoryService;
import core.game.item.ResourcesType;
import core.game.playground.MapBuilder;
import core.game.structures.buildings.ResourceSign;
import core.game.structures.Blueprint;
import core.game.structures.buildings.House;
import core.game.structures.buildings.Warehouse;
import core.game.structures.environment.GoldVein;
import core.game.structures.environment.Reclaimable;
import core.game.structures.environment.Tree;
import core.game.unit.Player;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GamePanel extends Screen implements ActionListener {

    private static final Logger LOGGER = LogManager.getLogger(GamePanel.class.getName());

	private Timer reclaimTimer;

	private MoveControl moveControl             = MoveControl.getInstance();
    private ActionControl actionControl         = ActionControl.getInstance();
	private InterfaceControl interfaceControl   = InterfaceControl.getInstance();
	private BuildMenuControl buildmenuControl   = BuildMenuControl.getInstance();
	private InventoryControl inventoryControl   = InventoryControl.getInstance();
	private DialogControl dialogControl         = DialogControl.getInstance();

	public static Player player;

	public static List<ArrayList<Blueprint>> buildableList;

	private boolean doReclaim;

	private Inventory inventory;

	private InventoryService inventoryService;

	public GamePanel(final long period, final int w, final int h, final Injector injector) {
        super(period, w, h);

        this.addKeyListener(GameKeyListener.getInstance());

		final InventoryService inventoryService = injector.getInstance(InventoryService.class);
		this.inventoryService = inventoryService;
		this.inventory = inventoryService.getUi();

        this.initGame();
	}

	private void initGame() {
        this.initBuildingList();

        doReclaim = false;

        MapBuilder.getInstance();
        new GoldVein(new Point(1,1));
        player = new Player(MapBuilder.getInstance().getDefaultSpawnPoint());

        reclaimTimer = new Timer(8000, this);
        reclaimTimer.start();

        LOGGER.info("Game are initiated");
        LOGGER.info(String.format("Game starts with %s objects", SpriteSet.getInstance().actorsSize()));
        LOGGER.info(String.format("The player: %s", player.toString()));
	}

	private void initBuildingList() {
		final ArrayList<Blueprint> natureBuildings = new ArrayList<Blueprint>();
        natureBuildings.add(Tree.getBlueprint());

		final ArrayList<Blueprint> civilBuildings = new ArrayList<Blueprint>();
		civilBuildings.add(House.getBlueprint());
		
		final ArrayList<Blueprint> resourceBuildings = new ArrayList<Blueprint>();
		resourceBuildings.add(Warehouse.getBlueprint());
		resourceBuildings.add(ResourceSign.getBlueprint(ResourcesType.WOOD));
		resourceBuildings.add(ResourceSign.getBlueprint(ResourcesType.GOLD));

		buildableList = new ArrayList<ArrayList<Blueprint>>();
        buildableList.add(natureBuildings);
        buildableList.add(civilBuildings);
        buildableList.add(resourceBuildings);
	}

    @Override
	protected void checkInput() {
        interfaceControl.showInterface();

		if (!interfaceControl.isShowBuildmenu()
				&& !interfaceControl.isShowInventory()
				&& !interfaceControl.isShowDialogBox()) {
            moveControl.move();
            actionControl.doAction();
		}

		if (interfaceControl.isShowBuildmenu()) {
            player.moveStop();
            buildmenuControl.selectElement();
		}

		if (interfaceControl.isShowInventory()) {
            player.moveStop();
            inventoryControl.selectElements();
		}
		
		if (interfaceControl.isShowDialogBox()) {
            player.moveStop();
            dialogControl.selectElement();
		}
	}

    @Override
	protected void checkTimeEvent() {
		if (!isPaused && !gameOver) {
            final Vector<Sprite> actors = SpriteSet.getInstance().getActors();
            if (doReclaim) {
                for (final Sprite sprite : actors) {
                    if (sprite instanceof Reclaimable) {
                        ((Reclaimable) sprite).reclaim();
                    }
                }
                doReclaim = false;
			}
		}
	}

    @Override
	protected void gameUpdate(final long timeDiff) {
		if (!isPaused && !gameOver) {
            final Vector<Sprite> actors = SpriteSet.getInstance().getClonedActors();

			for (final Sprite sprite : actors) {
				if (sprite instanceof GameObject) {
					((GameObject) sprite).doLogic(timeDiff);
				}
			}

			for (int i = 0; i < actors.size(); i++) {
				for (int n = i + 1; n < actors.size(); n++) {
					final Sprite spriteA = actors.get(i);
					final Sprite spriteB = actors.get(n);

					spriteA.collideWith(spriteB);
                    spriteB.collideWith(spriteA);
				}
			}

			this.inventoryService.update(player);
		}
	}

    @Override
	protected void gameRender() {
        super.gameRender();

        for (final Sprite sprite : SpriteSet.getInstance().getActors()) {
            if (sprite instanceof Drawable) {
                ((Drawable) sprite).draw(dbg);
            }
        }

		if (interfaceControl.isShowInventory()) {
			this.inventory.draw(dbg);
		}

		if (interfaceControl.isShowBuildmenu()) {
			BuildMenu.getInstance().draw(dbg);
		}
		
		if (this.interfaceControl.isShowDialogBox()) {
			DialogBox.getInstance().draw(dbg);
		}

        if (this.interfaceControl.isShowDebuggingBox()) {
            dbg.setColor(Color.BLACK);
            dbg.drawString("FPS: " + (int) averageFPS, 20, 25);
        }

//		PlayerBar.getInstance().draw(dbg);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(reclaimTimer)) {
            doReclaim = true;
		}
	}
}