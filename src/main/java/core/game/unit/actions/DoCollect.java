package core.game.unit.actions;

import core.engine.SpriteSet;
import core.game.item.ResourcesType;
import core.game.structures.Structure;
import core.game.structures.buildings.ResourceSign;
import core.game.structures.buildings.Warehouse;
import core.game.structures.environment.Resource;
import core.game.unit.Backpack;
import core.game.unit.NonPlayerCharacter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User: RICO
 * Date: 05.01.13
 * Time: 14:53
 */
public class DoCollect implements Doable, ActionListener {

    private enum CollectSteps {
        GO_TO_SIGN, SEARCH, COLLECT, GO_TO_WAREHOUSE, STORE
    }

    private final Timer collectTimer;

    private final Timer startAgainTimer;

    private NonPlayerCharacter npc;

    private CollectSteps currentStep;

    private ResourceSign resourceSign = null;

    private Warehouse warehouse = null;

    private Resource resource = null;

    private final Class ofClass;

    private boolean isRandomOrientationBlocked = false;

    public <T> DoCollect(final Class<T> ofClass) {
        this.ofClass = ofClass;
        currentStep = CollectSteps.GO_TO_SIGN;
        collectTimer = new Timer(3000, this);
        startAgainTimer = new Timer(4000, this);
    }

    public void doing(final NonPlayerCharacter npc) {
        this.npc = npc;

        switch (currentStep) {
            case GO_TO_SIGN -> goToSing();
            case SEARCH -> goToResource();
            case COLLECT -> collectTimer.start();
            case GO_TO_WAREHOUSE -> goToWarehouse();
            case STORE -> store();
        }
    }

    public boolean doCollide(final Structure structure) {
        if(npc.getActionArea().intersects(structure)) {
            if (structure.equals(resourceSign) && currentStep.equals(CollectSteps.GO_TO_SIGN)) {
                currentStep = CollectSteps.SEARCH;
                isRandomOrientationBlocked = false;
                return true;
            }

            if (structure.equals(warehouse) && currentStep.equals(CollectSteps.GO_TO_WAREHOUSE)) {
                currentStep = CollectSteps.STORE;
                isRandomOrientationBlocked = true;
                return true;
            }

            if (structure.getClass().equals(ofClass) && currentStep.equals(CollectSteps.SEARCH)) {
                currentStep = CollectSteps.COLLECT;
                isRandomOrientationBlocked = true;
                return true;
            }
        } else {
            if (!isRandomOrientationBlocked) {
                npc.setRandomOrientation();
            }
        }
        return false;
    }

    private void findResourceSign() {
        for (final ResourceSign resourceSign : SpriteSet.getInstance().getActorsByClass(ResourceSign.class)) {
            if (resourceSign.getResource().equals(ofClass)) {
                this.resourceSign = resourceSign;
                return;
            }
        }
        npc.setNextDoable(new DoGoHome());
    }

    private void findWarehouse() {
        final List<Warehouse> warehouseList = SpriteSet.getInstance().getActorsByClass(Warehouse.class);

        if(!warehouseList.isEmpty()) {
            warehouse = warehouseList.get(0);
        } else {
            npc.setNextDoable(new DoGoHome());
        }
    }

    @SuppressWarnings("unchecked")
    private void findResource() {
        resource = (Resource) SpriteSet.getInstance().getClosestActor(resourceSign.getPosition(), ofClass);
        if (null == resource) {
            npc.setNextDoable(new DoGoHome());
        }
    }

    private void goToSing() {
        if (null == resourceSign) {
            findResourceSign();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(resourceSign.getPosition());
        }
    }

    private void goToWarehouse() {
        if (null == warehouse) {
            findWarehouse();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(warehouse.getPosition() );
        }
    }

    private void goToResource() {
        if (null == resource || resource.isRemove()) {
            findResource();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(resource.getPosition());
        }
    }

    private void collect() {
        if (null == resource || resource.isRemove()) {
            currentStep = CollectSteps.SEARCH;
        }

        final var backpack = npc.getBackpack();
        if (backpack.getResourceSize() < Backpack.MAX_RESOURCE_CAPACITY) {
            final var resourcesType = resource.collect();
            if (null == resourcesType) {
                resource = null;
                currentStep = CollectSteps.SEARCH;
            } else {
                backpack.addResource(resourcesType);
            }
        } else {
            collectTimer.stop();
            currentStep = CollectSteps.GO_TO_WAREHOUSE;
        }
    }

    private void store() {
        if (!startAgainTimer.isRunning()) {
            final var backpack = npc.getBackpack();
            for (int i = 0; i < backpack.getResourceSize(); i++) {
                warehouse.storeResource(backpack.getResource(i));
                backpack.removeResource(i);
            }

            startAgainTimer.start();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(collectTimer)) {
            collect();
        }

        if (e.getSource().equals(startAgainTimer)) {
            currentStep = CollectSteps.GO_TO_SIGN;
            warehouse = null;
            resourceSign = null;
            startAgainTimer.stop();
        }
    }
}
