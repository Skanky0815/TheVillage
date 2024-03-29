package core.game.item;

import java.util.HashMap;
import java.util.Map;

import core.helper.Translator;

public class Resources {
	
	private static Resources instance;
	
	private final Map<ResourcesType, Resource> resources = new HashMap<>();

	private Resources() {
        resources.put(ResourcesType.GOLD, this.getGold());
        resources.put(ResourcesType.WOOD, this.getWood());
	}
	private Resource getGold() {
		final String name = Translator.translate("resource.gold.name");
		final String description = Translator.translate("resource.gold.description");
		return new Resource(ResourcesType.GOLD, name, description);
	}

	private Resource getWood() {
		final String name = Translator.translate("resource.wood.name");
		final String description = Translator.translate("resource.wood.description");
		return new Resource(ResourcesType.WOOD, name, description);
	}
	
	public static Resources getInstance() {
		if (null == instance) {
			instance = new Resources();
		}
		return instance;
	}
	
	public Resource getResource(ResourcesType key) {
		return resources.get(key);
	}
}
