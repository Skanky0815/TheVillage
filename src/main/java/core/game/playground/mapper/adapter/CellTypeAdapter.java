package core.game.playground.mapper.adapter;

import core.game.playground.CellType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 22:15
 */
public class CellTypeAdapter extends XmlAdapter<String, CellType> {

    @Override
    public CellType unmarshal(String cellType) throws Exception {
        return CellType.valueOf(cellType);
    }

    @Override
    public String marshal(CellType cellType) throws Exception {
        return cellType.name();
    }
}
