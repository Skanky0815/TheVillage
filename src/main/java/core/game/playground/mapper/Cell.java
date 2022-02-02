package core.game.playground.mapper;

import core.game.playground.CellType;
import core.game.playground.mapper.adapter.CellTypeAdapter;
import core.game.playground.mapper.adapter.PointAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.awt.*;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 21:18
 */
@XmlType
public class Cell {

    @XmlElement(name = "position", required = true)
    @XmlJavaTypeAdapter(PointAdapter.class)
    private Point position;

    @XmlElement(name = "type", required = true)
    @XmlJavaTypeAdapter(CellTypeAdapter.class)
    private CellType type;

    public Cell(final Point position, final CellType type) {
        this();

        this.position = position;
        this.type = type;
    }

    public Cell() { }

    @XmlTransient
    public Point getPosition() {
        return this.position;
    }

    @XmlTransient
    public CellType getType() {
        return this.type;
    }

    public void setPosition(final Point position) {
        this.position = position;
    }

    public void setType(final CellType type) {
        this.type = type;
    }
}
