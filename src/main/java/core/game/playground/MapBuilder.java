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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.TheVillage;
import core.engine.Drawable;
import core.engine.Sprite;
import core.game.playground.mapper.Map;
import core.helper.GuiDebugger;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Singleton
public final class MapBuilder extends Rectangle2D.Double implements Drawable {

    private static final String CELL_NOT_FOUND = "CELL_NOT_FOUND";

	private final PositionMapper positionMapper;

	private Vector<Sprite> cellList;

    private Point defaultSpawnPoint;

    private String mapFileName = "test_map_0.xml";

    @Inject
    private MapBuilder(final Logger logger, final PositionMapper positionMapper) {
		this.positionMapper = positionMapper;

		x = 50;
        y = 50;
        defaultSpawnPoint = new Point(5, 2);

        cellList = new Vector<>();
        loadMap();

        width = x * PositionMapper.SIZE;
        height = y * PositionMapper.SIZE;

        logger.info(String.format("Map \"%s\" created", mapFileName));
	}

    private void loadMap() {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Map.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            final String path = TheVillage.BASE_RESOURCES_PATH + "maps" + TheVillage.FS + mapFileName;
            final Map map = (Map) unmarshaller.unmarshal(new File(path));

            // TODO add default spawn point bay map param

            positionMapper.init(map.getWidth(), map.getHeight());

            for (final core.game.playground.mapper.Cell cell : map.getCellList()) {
                cellList.add(CellFactory.getCell(cell.getPosition(), cell.getType()));
            }
        } catch (JAXBException e) {
            MapBuilder.createTestMap();
            this.loadMap();
        }
    }

	/**
	 * Create a test map with 5x5 cells
	 */
	private static void createTestMap() {
        final List<core.game.playground.mapper.Cell> testCellList = new ArrayList<>();

        int line = 1;

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

        final Map map = new Map();
        map.setCellList(testCellList);
        map.setHeight(line);
        map.setWidth(line);

        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Map.class);

            final String path = TheVillage.BASE_RESOURCES_PATH + "maps" + TheVillage.FS + "test_map_0.xml";

            final Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(map, new FileOutputStream(path));
        } catch (Exception e){
            e.printStackTrace();
        }
	}

	/**
	 * Get a Cell by maps x and y coordinate
	 *
	 * @param x int
	 * @param y int
	 * @return Cell
	 */
    @SuppressWarnings("unused")
	public Cell getCellByPoint(final int x, final int y) throws IndexOutOfBoundsException {
		return getCellByPoint(new Point(x, y));
	}

	/**
	 * Get a Cell by a Point
	 *
	 * @param point Point
	 *
	 * @return Cell
	 */
	public Cell getCellByPoint(final Point point) {
		for (final Sprite cell : cellList) {
			if (cell.getPosition().equals(point) && cell instanceof Cell) {
				return (Cell) cell;
			}
		}
		
		throw new IndexOutOfBoundsException(MapBuilder.CELL_NOT_FOUND);
	}

	/**
	 * Get a Cell by the X and Y coordinate from the JPanel
	 *
	 * @param x double
	 * @param y double
	 *
	 * @return Cell
	 */
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

	/**
	 * Get the map as list for game logic
	 *
	 * @return Vector[Cell]
	 */
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
		return getClass().getName() + "@[numberOfCells=" + cellList.size() + "]";
	}
}
