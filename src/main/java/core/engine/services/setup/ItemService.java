package core.engine.services.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.engine.services.XMLReaderService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.helper.Config;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by RICO on 04.04.2015.
 *
 * The ItemService class load all resources xml files form the resources and generate a HashMap with representing
 * Resource classes
 */
@Singleton
public class ItemService extends AbstractSetupService {

    /**
     * A HashMap with all Resources with the ResourceType as Key
     */
    private HashMap<ResourcesType, Resource> resourceHashMap;

    /**
     * The Constructor
     *
     * @param log               The logging class
     * @param config            The game config class
     * @param xmlReaderService  The service to load the xml files
     */
    @Inject
    ItemService(final Logger log, final Config config, final XMLReaderService xmlReaderService) {
        super(log, config, xmlReaderService);

        resourceHashMap = new HashMap<>();

        init();
    }

    /**
     * Return the Resource with is represented by the given key
     *
     * @param key The ResourcesType with is use as key
     *
     * @return The founded Resource or null if not found
     */
    public Resource getItem(ResourcesType key) {
        if (resourceHashMap.containsKey(key)) {
            return resourceHashMap.get(key);
        }

        log.debug("Not item found for " + key.name());
        return null;
    }

    /**
     * This method load all resources form the resources dir and store them into the local HashMap
     */
    private void init() {
        for (final Object resource: xmlReaderService.loadAllXml("items/resources/", Resource.class)) {
            if (resource instanceof Resource) {
                resourceHashMap.put(((Resource) resource).getType(), (Resource) resource);
            }
        }
    }
}