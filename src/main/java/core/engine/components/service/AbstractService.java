package core.engine.components.service;

import core.helper.Config;
import org.apache.log4j.Logger;

/**
 * Created by RICO on 05.04.2015.
 */
public abstract class AbstractService {

    protected Logger log;

    protected Config config;

    public AbstractService(final Logger log, final Config config) {
        this.log = log;
        this.config = config;
    }
}
