package core.engine.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import core.engine.components.service.AbstractService;
import core.helper.Config;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Created by RICO on 04.04.2015.
 */
public class XMLReaderService extends AbstractService {

    @Inject
    public XMLReaderService(final Logger log, final Config config) {
        super(log, config);
    }

    public List<Object> loadAllXml(final String filePath, final Class className) {
        final var folder = new File(filePath);
        if (!folder.isDirectory()) {
            this.log.error("Given path '" + filePath + "' is not a folder or do not exists!");
            return new ArrayList<>();
        }

        final ArrayList<Object> objects = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                objects.add(this.loadXml(fileEntry, className));
            }
        }

        return objects;
    }

    public Object loadXml(final File file, final Class className) {
        try {
            final var jaxbContext = JAXBContext.newInstance(className);
            final var unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            this.log.error(e);
            this.log.error("Could not load the given file '" + file.getAbsolutePath() + "'!");
        }

        return null;
    }

    public Object loadXml(final String filePath, final Class className) {
        final File file =  new File(filePath);
        return this.loadXml(file, className);
    }
}
