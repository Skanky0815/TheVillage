package core.game.item;

import java.awt.image.BufferedImage;

import core.engine.services.InterfaceXMLEntity;
import core.game.item.adapter.ResourcesTypeAdapter;
import core.helper.ImageLoader;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "item")
public class Resource implements InterfaceXMLEntity {

	private BufferedImage sIcon;

	@XmlElement(name = "type")
	@XmlJavaTypeAdapter(ResourcesTypeAdapter.class)
	private ResourcesType type;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "description")
	private String description;

	public Resource() {	}

	public Resource(final ResourcesType type, final String name, final String description) {
		this.type = type;
		this.name = name;
		this.description = description;

        sIcon = ImageLoader.getRescourcesIcons(type);
	}

	public final String getName() {
		return name;
	}

	@XmlTransient
	public final void setName(final String name) {
		this.name = name;
	}
	
	public final String getDescription() {
		return description;
	}

	@XmlTransient
	public final void setDescription(final String description) {
		this.description = description;
	}

	public final ResourcesType getType() {
		return type;
	}

	@XmlTransient
	public final void setType(final ResourcesType type) {
		this.type = type;
	}

	public final BufferedImage getSIcon() {
		return sIcon;
	}
}
