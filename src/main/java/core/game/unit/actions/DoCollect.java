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

    private Timer collectTimer;

    private Timer startAgainTimer;

    private NonPlayerCharacter npc;

    private CollectSteps currentStep;

    private ResourceSign resourceSign = null;

    private Warehouse warehouse = null;

    private Resource resource = null;

    private Class ofClass;

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
            case GO_TO_SIGN:
                this.goToSing();
                break;
            case SEARCH:
                this.goToResource();
                break;
            case COLLECT:
                collectTimer.start();
                break;
            case GO_TO_WAREHOUSE:
                this.goToWarehouse();
                break;
            case STORE:
                this.store();
                break;
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
            this.warehouse = warehouseList.get(0);
        } else {
            npc.setNextDoable(new DoGoHome());
        }
    }

    @SuppressWarnings("unchecked")
    private void findResource() {
        resource = (Resource) SpriteSet.getInstance().getClosestActor(resourceSign.getPosition(), ofClass);
        if (resource == null) {
            npc.setNextDoable(new DoGoHome());
        }
    }

    private void goToSing() {
        if (resourceSign == null) {
            this.findResourceSign();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(resourceSign.getPosition());
        }
    }

    private void goToWarehouse() {
        if (warehouse == null) {
            this.findWarehouse();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(warehouse.getPosition() );
        }
    }

    private void goToResource() {
        if (resource == null || resource.isRemove()) {
            this.findResource();
        } else {
            isRandomOrientationBlocked = false;
            npc.setWaypoint(resource.getPosition());
        }
    }

    private void collect() {
        if (resource == null || resource.isRemove()) {
            currentStep = CollectSteps.SEARCH;
        }

        final Backpack backpack = npc.getBackpack();
        if (backpack.getResourceSize() < Backpack.MAX_RESOURCE_CAPACITY) {
            final ResourcesType resourcesType = resource.collect();
            if (resourcesType == null) {
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
            final Backpack backpack = npc.getBackpack();
            for (int i = 0; i < backpack.getResourceSize(); i++) {
                warehouse.storeResource(backpack.getResource(i));
                backpack.removeResource(i);
            }

            startAgainTimer.start();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(collectTimer)) {
            this.collect();
        }

        if (e.getSource().equals(startAgainTimer)) {
            currentStep = CollectSteps.GO_TO_SIGN;
            warehouse = null;
            resourceSign = null;
            startAgainTimer.stop();
        }
    }
}
