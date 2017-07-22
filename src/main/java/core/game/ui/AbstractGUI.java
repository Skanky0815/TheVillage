package core.game.ui;

import core.engine.Drawable;
import core.engine.services.TranslateService;
import org.apache.log4j.Logger;

import java.awt.geom.Rectangle2D;

/**
 * Created by RICO on 05.04.2015.
 */
public abstract class AbstractGUI extends Rectangle2D.Double implements Drawable {

    protected TranslateService translator;

    protected Logger log;

    public void setTranslator(final TranslateService translator) {
        this.translator = translator;
    }

    public TranslateService getTranslator() {
        return translator;
    }

    public void setLog(final Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return this.log;
    }

}
