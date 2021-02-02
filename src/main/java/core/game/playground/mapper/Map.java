package core.game.playground.mapper;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 19:41
 */
@XmlRootElement(name = "map")
public final class Map {

    @XmlElement(name = "cell")
    private final List<Cell> cellList = new ArrayList<>();

    @XmlElement(name = "width", required = true)
    private int width;

    @XmlElement(name = "height", required = true)
    private int height;

    public Map() { }

    @XmlTransient
    public List<Cell> getCellList() {
        return cellList;
    }

    @XmlTransient
    public int getWidth() {
        return width;
    }

    @XmlTransient
    public int getHeight() {
        return height;
    }

    public void setCellList(final List<Cell> cellList) {
        this.cellList.addAll(cellList);
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }
}
