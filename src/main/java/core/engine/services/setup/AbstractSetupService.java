package core.engine.services.setup;

import core.engine.components.service.AbstractService;
import core.engine.services.XMLReaderService;
import core.helper.Config;
import org.apache.log4j.Logger;

/**
 * Created by RICO on 04.04.2015.
 */
abstract class AbstractSetupService extends AbstractService implements InterfaceSetupService {

    final XMLReaderService xmlReaderService;

    AbstractSetupService(final Logger log, final Config config, final XMLReaderService xmlReaderService) {
        super(log, config);
        this.xmlReaderService = xmlReaderService;
    }
}
