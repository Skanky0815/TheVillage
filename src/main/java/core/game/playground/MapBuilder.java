package core.game.playground;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import core.engine.Drawable;
import core.engine.Sprite;
import core.game.playground.mapper.Map;
import core.helper.GuiDebugger;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


public final class MapBuilder extends Rectangle2D.Double implements Drawable {

    private static final String CELL_NOT_FOUND = "CELL_NOT_FOUND";

    private static final MapBuilder instance = new MapBuilder();

	private final Vector<Sprite> cellList;

    private final Point defaultSpawnPoint;

    private MapBuilder() {
        x = 50;
        y = 50;
        defaultSpawnPoint = new Point(5, 2);

        cellList = new Vector<>();
        loadMap();

        width = x * PositionMapper.SIZE;
        height = y * PositionMapper.SIZE;
	}

    public static MapBuilder getInstance() {
        return instance;
    }

    private void loadMap() {
        try {
            final var jaxbContext = JAXBContext.newInstance(Map.class);
            final var unmarshaller = jaxbContext.createUnmarshaller();

            final var path = getClass().getClassLoader().getResource("maps/test_map_0.xml").getPath();
            final Map map = (Map) unmarshaller.unmarshal(new File(path));

            // TODO add default spawn point bay map param

            PositionMapper.getInstance().init(map.getWidth(), map.getHeight());

            for (final core.game.playground.mapper.Cell cell : map.getCellList()) {
                cellList.add(CellFactory.getCell(cell.getPosition(), cell.getType()));
            }
        } catch (JAXBException e) {
          createTestMap();
          loadMap();
        }
    }

	/**
	 * Create a test map with 5x5 cells
	 */
	public void createTestMap() {
		final List<core.game.playground.mapper.Cell> testCellList = new ArrayList<>();

		var line = 1;

		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.STREET));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		line++;
		testCellList.add(new core.game.playground.mapper.Cell(new Point(1, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(2, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(3, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(4, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(5, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(6, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(7, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(8, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(9, line), CellType.GRASS));
		testCellList.add(new core.game.playground.mapper.Cell(new Point(10, line), CellType.GRASS));

		final var map = new Map();
		map.setCellList(testCellList);
		map.setHeight(line);
		map.setWidth(line);

		try {
			final var path = getClass().getClassLoader().getResource("maps/test_map_0.xml").getPath();
			final var jc = JAXBContext.newInstance(Map.class);
			final var marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(map, new FileOutputStream(path));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    @SuppressWarnings("unused")
	public Cell getCellByPoint(final int x, final int y) throws IndexOutOfBoundsException {
		return getCellByPoint(new Point(x, y));
	}

	public Cell getCellByPoint(final Point point) {
		for (final Sprite cell : cellList) {
			if (cell.getPosition().equals(point) && cell instanceof Cell) {
				return (Cell) cell;
			}
		}
		
		throw new IndexOutOfBoundsException(MapBuilder.CELL_NOT_FOUND);
	}

    @SuppressWarnings("unused")
	public Cell getCellByPix(final double x, final double y) {
		for (final Sprite cell : cellList) {
			if (cell.contains(x, y) && cell instanceof Cell) {
				return (Cell) cell;
			}
		}

		throw new IndexOutOfBoundsException(MapBuilder.CELL_NOT_FOUND);
	}

    public Point getDefaultSpawnPoint() {
        return new Point(defaultSpawnPoint);
    }

    @SuppressWarnings("unused")
	public Vector<Sprite> getCellList() {
		return this.cellList;
	}

	public void draw(final Graphics g) {
		if (GuiDebugger.isDebugModeOn()) {
			g.setColor(Color.GREEN);
			g.fillRect(
                    (int) x,
                    (int) y,
                    (int) (x + width),
					(int) (y + height)
            );
		}
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "@[numberOfCells=" + cellList.size() + "]";
	}
}
