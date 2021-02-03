package core.engine.services.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.engine.services.XMLReaderService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.helper.Config;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Created by RICO on 04.04.2015.
 */
@Singleton
public class ItemService extends AbstractSetupService {

    private final HashMap<ResourcesType, Resource> resourceHashMap;

    @Inject
    ItemService(final Logger log, final Config config, final XMLReaderService xmlReaderService) {
        super(log, config, xmlReaderService);

        this.resourceHashMap = new HashMap<>();

        this.init();
    }

    public Resource getItem(final ResourcesType key) {
        if (resourceHashMap.containsKey(key)) {
            return resourceHashMap.get(key);
        }

        log.debug("Not item found for " + key.name());
        return null;
    }

    private void init() {
        final var path = getClass().getClassLoader().getResource("items/resources").getPath();
        for (final Object resource: xmlReaderService.loadAllXml(path, Resource.class)) {
            if (resource instanceof Resource) {
                resourceHashMap.put(((Resource) resource).getType(), (Resource) resource);
            }
        }
    }
}