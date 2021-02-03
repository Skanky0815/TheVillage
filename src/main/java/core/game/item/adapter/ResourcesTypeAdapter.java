package core.game.item.adapter;

import core.game.item.ResourcesType;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * Created by RICO on 05.04.2015.
 */
public class ResourcesTypeAdapter extends XmlAdapter<String, ResourcesType> {

    @Override
    public ResourcesType unmarshal(String resourcesType) throws Exception {
        return ResourcesType.valueOf(resourcesType);
    }

    @Override
    public String marshal(ResourcesType resourcesType) throws Exception {
        return resourcesType.name();
    }
}
