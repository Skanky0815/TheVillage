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

import org.apache.log4j.Logger;

public class GamePanel extends Screen implements ActionListener {

	public static Player player;

	public static List<ArrayList<Blueprint>> buildableList;

	private final Timer reclaimTimer;
	private final Logger logger;
	private final MoveControl moveControl;
    private final ActionControl actionControl;
	private final InterfaceControl interfaceControl;
	private final BuildMenuControl buildmenuControl;
	private final InventoryControl inventoryControl;
	private final DialogControl dialogControl;
	private final InventoryService inventoryService;
	private final MapBuilder mapBuilder;
	private final Inventory inventory;
	private final SpriteSet spriteSet;
	private final BuildMenu buildMenu;
	private final DialogBox dialogBox;

	private boolean doReclaim;

	GamePanel(final long period, final int w, final int h, final Injector injector) {
        super(period, w, h);

        logger = injector.getInstance(Logger.class);
		mapBuilder = injector.getInstance(MapBuilder.class);
		moveControl = injector.getInstance(MoveControl.class);
		actionControl = injector.getInstance(ActionControl.class);
		interfaceControl = injector.getInstance(InterfaceControl.class);
		buildmenuControl = injector.getInstance(BuildMenuControl.class);
		inventoryControl = injector.getInstance(InventoryControl.class);
		dialogControl = injector.getInstance(DialogControl.class);
		inventoryService = injector.getInstance(InventoryService.class);
		spriteSet = injector.getInstance(SpriteSet.class);
		buildMenu = injector.getInstance(BuildMenu.class);
		dialogBox = injector.getInstance(DialogBox.class);

		reclaimTimer = new Timer(8000, this);

		inventory = inventoryService.getUi();

		addKeyListener(injector.getInstance(GameKeyListener.class));
		initGame();
	}

	private void initGame() {
        initBuildingList();

        doReclaim = false;

        new GoldVein(new Point(1,1));
        player = new Player(mapBuilder.getDefaultSpawnPoint());

        reclaimTimer.start();

        logger.info("Game are initiated");
        logger.info(String.format("Game starts with %s objects", spriteSet.actorsSize()));
        logger.info(String.format("The player: %s", player.toString()));
	}

	private void initBuildingList() {
		final ArrayList<Blueprint> natureBuildings = new ArrayList<>();
        natureBuildings.add(Tree.getBlueprint());

		final ArrayList<Blueprint> civilBuildings = new ArrayList<>();
		civilBuildings.add(House.getBlueprint());
		
		final ArrayList<Blueprint> resourceBuildings = new ArrayList<>();
		resourceBuildings.add(Warehouse.getBlueprint());
		resourceBuildings.add(ResourceSign.getBlueprint(ResourcesType.WOOD));
		resourceBuildings.add(ResourceSign.getBlueprint(ResourcesType.GOLD));

		buildableList = new ArrayList<>();
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
		// if pause, game over or reclaim disabled then do nothing
		if (isPaused || gameOver || !doReclaim) {
			return;
		}

		// loop over all screen elements and if the reclaimable call the reclaim method
		for (final Sprite sprite : spriteSet.getActors()) {
			if (sprite instanceof Reclaimable) {
				((Reclaimable) sprite).reclaim();
			}
		}

		// set the reclaim on false
		doReclaim = false;
	}

    @Override
	protected void gameUpdate(final long timeDiff) {
		// if pause or the games is over then do nothing
		if (isPaused || gameOver) {
			return;
		}

		// get clone of all actors
		final Vector<Sprite> actors = spriteSet.getClonedActors();

		// loop over all actors an call the doLogic method
		for (final Sprite actor : actors) {
			if (!(actor instanceof GameObject)) {
				continue;
			}

			GameObject gameObject = (GameObject) actor;

			gameObject.doLogic(timeDiff);

			// remove all marked actors
			if (gameObject.isRemove()) {
				spriteSet.removeActor(gameObject);
			}
		}

		// check if an actors collide with each other actor
		for (int i = 0; i < actors.size(); i++) {
			for (int n = i + 1; n < actors.size(); n++) {
				final Sprite spriteA = actors.get(i);
				final Sprite spriteB = actors.get(n);

				spriteA.collideWith(spriteB);
				spriteB.collideWith(spriteA);
			}
		}

		// update the inventoryService with the player data
		inventoryService.update(player);
	}

    @Override
	protected void gameRender() {
        super.gameRender();

        for (final Sprite sprite : spriteSet.getActors()) {
            if (sprite instanceof Drawable) {
                ((Drawable) sprite).draw(dbg);
            }
        }

		if (interfaceControl.isShowInventory()) {
			this.inventory.draw(dbg);
		}

		if (interfaceControl.isShowBuildmenu()) {
			buildMenu.draw(dbg);
		}
		
		if (interfaceControl.isShowDialogBox()) {
			dialogBox.draw(dbg);
		}

        if (interfaceControl.isShowDebuggingBox()) {
            dbg.setColor(Color.BLACK);
            dbg.drawString("FPS: " + (int) averageFPS, 20, 25);
        }

//		PlayerBar.getInstance().draw(dbg);
	}

	public void actionPerformed(ActionEvent e) {

		// check if the reclaim timer is triggered and set the reclaim var
		doReclaim = e.getSource().equals(reclaimTimer);
	}
}