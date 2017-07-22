package core.engine.services.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.engine.services.XMLReaderService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.helper.Config;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RICO on 04.04.2015.
 */
@Singleton
public class ItemService extends AbstractSetupService implements InterfaceSetupService {

    private HashMap<ResourcesType, Resource> resourceHashMap;

    @Inject
    public ItemService(Logger log, Config config, XMLReaderService xmlReaderService) {
        super(log, config, xmlReaderService);

        this.resourceHashMap = new HashMap<ResourcesType, Resource>();

        this.setup();
    }

    public void setup() {
        final ArrayList<Object> items  = this.xmlReaderService.loadAllXml("items/resources/", Resource.class);

        for (final Object o: items) {
            if (o instanceof Resource) {
                final Resource resource = (Resource) o;
                this.resourceHashMap.put(resource.getType(), resource);
            }
        }
    }

    public Resource getItem(ResourcesType key) {
        if (resourceHashMap.containsKey(key)) {
            return this.resourceHashMap.get(key);
        }

        log.debug("Not item found for " + key.name());
        return null;
    }
}
