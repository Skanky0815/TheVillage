package core.engine.services;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

import com.google.inject.Inject;
import core.TheVillage;
import core.engine.components.service.AbstractService;
import core.helper.Config;
import org.apache.log4j.Logger;

/**
 * Created by RICO on 04.04.2015.
 */
public class XMLReaderService extends AbstractService {

    private String rootPath = TheVillage.BASE_RESOURCES_PATH;

    @Inject
    public XMLReaderService(final Logger log, final Config config) {
        super(log, config);
    }

    public ArrayList<Object> loadAllXml(final String filePath, final Class className) {
        final File folder = new File(rootPath + filePath);
        if (!folder.isDirectory()) {
            log.error("Given path '" + filePath + "' is not a folder or do not exists!");
            return null;
        }

        final ArrayList<Object> objects = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                objects.add(this.loadXml(fileEntry, className));
            }
        }

        return objects;
    }

    private Object loadXml(final File file, final Class className) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(className);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(e);
            log.error("Could not load the given file '" + file.getAbsolutePath() + "'!");
        }

        return null;
    }

    public Object loadXml(final String filePath, final Class className) {
        return loadXml(new File(this.rootPath + filePath), className);
    }
}
