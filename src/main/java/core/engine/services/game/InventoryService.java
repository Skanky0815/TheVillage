package core.engine.services.game;

import com.google.inject.Inject;
import core.engine.components.service.AbstractService;
import core.engine.services.TranslateService;
import core.engine.services.setup.ItemService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.game.ui.Inventory;
import core.game.unit.Player;
import core.helper.Config;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by RICO on 05.04.2015.
 */
public class InventoryService extends AbstractService implements InterfaceRenderInventoryService {

    public final TranslateService translator;

    private final ItemService itemService;

    private Inventory ui;

    private List<ResourcesType> itemList;

    @Inject
    public InventoryService(
            final Logger log,
            final Config config,
            final TranslateService translator,
            final ItemService itemService,
            final Inventory ui
    ) {
        super(log, config);
        this.translator = translator;
        this.itemService = itemService;
        this.ui = ui;
        this.itemList = new ArrayList<>();
        this.ui.setInventoryService(this);
    }

    public Inventory getUi() {
        return this.ui;
    }

    public void update(final Player player) {
        for (final Map.Entry<ResourcesType, Integer> e : player.getBackpack().entrySet()) {
            itemList.add(e.getKey());
        }
    }

    public Resource getResource(int item) {
        if (item == this.getItemCount()) {
            item = 0;
        }

        if (item < 0) {
            item = this.getItemCount() + item;
        }

        return this.itemService.getItem(itemList.get(item));
    }

    public int getItemCount() {
        return this.itemList.size();
    }
}
