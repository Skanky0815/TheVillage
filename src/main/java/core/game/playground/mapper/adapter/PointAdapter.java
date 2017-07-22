package core.game.playground.mapper.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.*;
import java.util.Arrays;

/**
 * User: RICO
 * Date: 23.12.12
 * Time: 22:05
 */
public class PointAdapter extends XmlAdapter<String, Point> {

    @Override
    public Point unmarshal(String position) throws Exception {
        final String[] split = position.split("\\|");
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    public String marshal(Point position) throws Exception {
        return position.x + "|" + position.y;
    }
}
