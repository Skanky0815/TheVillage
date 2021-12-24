package core.game.ui;

import core.engine.Drawable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public final class DebuggingBox extends Rectangle2D.Double implements Drawable {

    private static DebuggingBox instance;

    private final List<String> output;

    private static final int MAX_STRINGS = 10;

    public static DebuggingBox getInstance() {
        if (instance == null) {
            instance = new DebuggingBox();
        }
        return instance;
    }

    private DebuggingBox() {
        this.output = new ArrayList<>();

        width = 400;
        height = 200;
        x = 0;
        y = GameFrame.pHeight - height;
    }

    public void addString(String s) {
        if (output.size() == DebuggingBox.MAX_STRINGS) {
            output.remove(0);
        }
        output.add(s);
    }

    public void clearDebuggingBox() {
        output.clear();
    }

    @Override
    public void draw(final Graphics g) {

        final int x = (int) this.x;
        final int stringX = x + 5;
        final int y = (int) this.y;
        int i = 1;

        g.setColor(Color.GRAY);
        g.fillRect(x, y, (int) width, (int) height);
        g.setColor(Color.BLACK);
        for (final String s : output) {
            g.drawString(s, stringX, y + (16 * i));
            i++;
        }
        g.drawRect(x, y, (int) width, (int) height);
    }
}
