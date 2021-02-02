package core.engine.services;

import java.io.File;
import java.util.ArrayList;

import com.google.inject.Inject;
import core.TheVillage;
import core.engine.components.service.AbstractService;
import core.helper.Config;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.Logger;

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
        final File folder = new File(this.rootPath + filePath);
        if (!folder.isDirectory()) {
            this.log.error("Given path '" + filePath + "' is not a folder or do not exists!");
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

    public Object loadXml(final File file, final Class className) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(className);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            this.log.error(e);
            this.log.error("Could not load the given file '" + file.getAbsolutePath() + "'!");
        }

        return null;
    }

    public Object loadXml(final String filePath, final Class className) {
        final File file =  new File(this.rootPath + filePath);
        return this.loadXml(file, className);
    }
}
