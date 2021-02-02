package core.game.unit;

import java.awt.*;
import java.util.Random;

import core.engine.Integrable;
import core.engine.MoveTo;
import core.engine.Sprite;
import core.engine.ki.MyAStar;
import core.engine.ki.MyAStar.*;
import core.game.dialog.Dialog;
import core.game.dialog.Speakable;
import core.game.playground.Cell;
import core.game.structures.Structure;
import core.game.ui.DialogBox;
import core.game.unit.actions.DoNothing;
import core.game.unit.actions.Doable;
import core.helper.GuiDebugger;

public abstract class NonPlayerCharacter extends Unit implements Integrable {

    private static final DialogBox DIALOG_BOX = DialogBox.getInstance();

    private final Backpack backpack = new Backpack();

    protected Doable doable = new DoNothing();

    protected Speakable speakable;

    protected Structure home;

    private Doable doNext = null;

    private Node waypoint;

    public NonPlayerCharacter(final Point position, final Structure home) {
        super(position);
        this.home = home;
    }

    @Override
    public void interact(final Player player) {
        DIALOG_BOX.setNpc(this);
        DIALOG_BOX.setIsShowed(true);
        this.lookAt(player.getOrientation());
        this.removeWaypoint();
    }

    public final Structure getHome() {
        return home;
    }

    public final void setHome(final Structure home) {
        this.home = home;
    }

    public final void setDoable(final Doable doable) {
        this.doable = doable;
    }

    public void doLogic(final long delta) {
        super.doLogic(delta);

        this.doNextDoable();
        this.doDoing();
    }

    public final void doDoing() {
        doable.doing(this);
    }

    @Override
    public void move(final long delta) {
        if (waypoint != null && waypoint.hasParent()) {
            final Sprite goal = waypoint.getParent().getCell();
            boolean finX = false;
            boolean finY = false;
            if (dx != 0) {
                double newX = x + dx * (delta / 1e9);
                if ((dx > 0 && newX > goal.x) || (dx < 0 && newX < goal.x)) {
                    newX = goal.x;
                    finX = true;
                }
                x = newX;
            }

            if (dy != 0) {
                double newY = y + dy * (delta / 1e9);
                if ((dy > 0 && newY > goal.y) || (dy < 0 && newY < goal.y)) {
                    newY = goal.y;
                    finY = true;
                }
                y = newY;
            }

            if ((dx != 0 && finX) || (dy != 0 && finY)) {
                this.setPosition(goal.getPosition());
                ((Cell) goal).entersCell();
            }

            this.moveToWaypoint();
        }
    }


    public boolean isMoving() {
        return (dx != 0) || (dy != 0);
    }

    @Override
    public boolean collideWith(final Sprite sprite) {
        boolean isCollided = false;
//        if (sprite instanceof Cell) {
//            if (this.cellCollidedTest(sprite)) {
//                this.setPosition(sprite.getPosition());
//                ((Cell) sprite).entersCell();
//                isCollided = true;
//            }
//        }

        if (sprite instanceof Structure && !this.isMoving() && !(doable instanceof DoNothing)) {
            if (doable.doCollide((Structure) sprite)) {
                isCollided = true;
            }
        }

        return isCollided;
    }

    private boolean cellCollidedTest(final Sprite sprite) {
        return waypoint != null && waypoint.hasParent() && waypoint.getParent().getCell().equals(sprite)
                && x == sprite.x && y == sprite.y;
    }

    /*
    * @return Dialog
    */
    public final Dialog doSpeak() {
        return speakable.speak();
    }

    public void lookAt(final MoveTo direction) {
        switch (direction) {
            case N:
                orientation = MoveTo.S;
                break;
            case S:
                orientation = MoveTo.N;
                break;
            case E:
                orientation = MoveTo.W;
                break;
            case W:
                orientation = MoveTo.E;
                break;
        }
    }

    /**
     * Set a Waypoint on the position from the x and y coordinate (Point)
     *
     * @param x int
     * @param y int
     */
    public void setWaypoint(final int x, final int y) {
        this.setWaypoint(new Point(x, y));
    }

    /**
     * Set a Waypoint on the position from the Point
     *
     * @param position Point
     */
    public void setWaypoint(final Point position) {
        this.calculateWayToWaypoint(position);
    }

    /**
     * Calculate the Way to the Waypoint with the A* algorithm
     */
    private void calculateWayToWaypoint(final Point position) {
        try {
            waypoint = MyAStar.getPathInArray(position, this);
        } catch (final InterruptedException e) {
        }
    }

    private void moveToWaypoint() {
        if (!this.getPosition().equals(waypoint.getParent().getPosition())) {
            switch (waypoint.getMoveTo()) {
                case N:
                    this.moveDown();
                    break;
                case S:
                    this.moveUp();
                    break;
                case W:
                    this.moveRight();
                    break;
                case E:
                    this.moveLeft();
                    break;
            }
        } else {
            this.moveStop();
            if (waypoint.hasParent()) {
                waypoint = waypoint.getParent();
            } else {
                this.removeWaypoint();
            }
        }
    }

    /**
     * remove the current waypoint an stop the movement
     */
    public final void removeWaypoint() {
        waypoint = null;
        this.moveStop();
    }

    @Override
    public void draw(final Graphics g) {
        super.draw(g);

        if (GuiDebugger.isDebugModeOn() && waypoint != null) {
            this.drawRoute(g);
        }
    }

    private void drawRoute(final Graphics g) {
        Node testNode = waypoint;
        g.setColor(new Color(255, 255, 255));
        while (testNode.hasParent()) {
            g.drawLine(
                    (int) testNode.getCell().getCenterX(),
                    (int) testNode.getCell().getCenterY(),
                    (int) testNode.getParent().getCell().getCenterX(),
                    (int) testNode.getParent().getCell().getCenterY()
            );

            testNode = testNode.getParent();
        }
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setRandomOrientation() {
        final MoveTo moveTo[] = MoveTo.values();
        final Random random = new Random();
        orientation = moveTo[random.nextInt(moveTo.length)];
    }

    public void setNextDoable(final Doable doable) {
        doNext = doable;
    }

    private void doNextDoable() {
        if (doNext != null) {
            doable = doNext;
            doNext = null;
        }
    }
}