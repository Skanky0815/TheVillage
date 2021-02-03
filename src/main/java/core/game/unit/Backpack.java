package core.game.unit;

import core.game.item.ResourcesType;
import core.game.structures.environment.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * User: RICO
 * Date: 10.01.13
 * Time: 22:14
 */
public class Backpack {

    public static final int MAX_RESOURCE_CAPACITY = 10;

    private final ArrayList<ResourcesType> resourceList;

    public Backpack() {
        resourceList = new ArrayList<>(MAX_RESOURCE_CAPACITY);
    }

    public <T extends Resource> void addResource(final ResourcesType resourcesType) {
        resourceList.add(resourcesType);
    }

    public ResourcesType getResource(final int index) {
        return resourceList.get(index);
    }

    public List<ResourcesType> getAllResource() {
        return resourceList;
    }

    public int getResourceSize() {
        return resourceList.size();
    }

    public Object[] resourcesToArray() {
        return resourceList.toArray();
    }

    public void removeResource(final int index) {
        resourceList.remove(index);
    }
}
