package core.engine.ki;

import java.util.List;
import java.awt.Point;
import java.util.Vector;

import core.game.playground.MapBuilder;
import core.game.unit.Unit;
import core.game.playground.Cell;
import core.engine.MoveTo;

public class MyAStar {

    private static final String NO_NODE_FOUND = "No Node found";

	public final static class Node {

		private Node parent = null;

		private MoveTo moveTo;

		private final Cell cell;

		private final Point position;

		private float f, g, h;

		public Node getParent() {
			return parent;
		}

		public void setMoveTo(final MoveTo moveTo) {
			this.moveTo = moveTo;
		}

		public Point getPosition() {
			return position;
		}

		public MoveTo getMoveTo() {
			return moveTo;
		}

		public Cell getCell() {
			return cell;
		}

		public float getF() {
			return f;
		}

		private Node(final Node parent, final Cell cell, final float g, final float h, final Point position) {
			this(parent, cell, position);
			this.g = g;
			this.h = h;
			this.f = g + f;
		}

		private Node(final Node parent, final Cell cell, final Point position) {
			this.parent = parent;
			this.cell = cell;
			this.position = position;
		}

		@Override
		public String toString() {
			return getClass().getName() + "@[position=" + position.x + "|"
					+ position.y + ", moveTo=" + moveTo + ", parent=" + parent + "]";
		}

        public boolean hasParent() {
            return null != parent;
        }
	}

	public static Node getPathInArray(final Point goal, final Unit unit) throws InterruptedException {

		final List<Node> openList = new Vector<>();
		final List<Node> closedList = new Vector<>();

        final var mapBuilder = MapBuilder.getInstance();
		final var startCell = mapBuilder.getCellByPoint(goal);
		final var startNode = new Node(null, startCell, 0, 0, unit.getPosition());

		openList.add(startNode);
		while (!openList.isEmpty()) {
			final var node = getLastF(openList);
			openList.remove(node);

			final Vector<Node> successors = new Vector<>();

			for (final var moveTo : MoveTo.values()) {
				MyAStar.testNextNode(node, moveTo, successors, unit.getPosition());
			}

			for (final Node successorNode : successors) {
				if (successorNode.getPosition().equals(unit.getPosition())) {
					return successorNode;
				}

				boolean add = true;
				if (MyAStar.betterAt(successorNode, openList)) {
					add = false;
				}

				if (MyAStar.betterAt(successorNode, closedList)) {
					add = false;
				}

				if (add) {
					openList.add(successorNode);
				}
			}

			closedList.add(node);
		}

		throw new InterruptedException(NO_NODE_FOUND);
	}

	private static boolean betterAt(final Node successorNode, final List<Node> openList) {
		for (final var node : openList) {
			if (node.getPosition().equals(successorNode.getPosition())
					&& node.getF() <= successorNode.getF()) {
				return true;
			}
		}
		return false;
	}

	private static void testNextNode(final Node node, final MoveTo moveTo, final Vector<Node> successors, final Point goal) {
		final var x = node.getCell().getPosition().x + moveTo.getX();
		final var y = node.getCell().getPosition().y + moveTo.getY();
		final var newPosition = new Point(x, y);

		try {
            final var mapBuilder = MapBuilder.getInstance();
			final var cell = mapBuilder.getCellByPoint(newPosition);
            if (cell.canGo() || cell.getPosition().equals(goal)) {
                final var n = new Node(node, cell, newPosition);
                n.setMoveTo(moveTo);
                MyAStar.calcNode(n, goal, successors);
            }
		} catch (IndexOutOfBoundsException ignored) { }
	}

	private static Node getLastF(final List<Node> openList) {
		Node last = null;
		for (final Node node : openList) {
			if ((null == last) || (node.getF() < last.getF())) {
				last = node;
			}
		}
		return last;
	}

	private static void calcNode(final Node node, final Point goal, final Vector<Node> successors) {
		node.g = calcG(node);
		node.h = calcH(node, goal);

		var value = switch (node.getCell().getType()) {
			case GRASS -> 2;
			case PATH -> 1;
			case WOOD -> 6;
			default -> 0;
		};

		node.g += value;
		node.f = node.g + node.h;
		successors.add(node);
	}

	private static float calcH(final Node node, final Point goal) {
		final var distX = Math.abs(node.getPosition().x - goal.x);
		final var distY = Math.abs(node.getPosition().y - goal.y);

		return (float) Math.sqrt(distX * distX + distY * distY);
	}

	private static float calcG(final Node node) {
		final var newNode = node.getParent();
		return newNode.g + 1;
	}
}