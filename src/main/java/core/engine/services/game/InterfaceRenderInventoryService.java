package core.engine.services.game;

import core.game.item.Resource;

/**
 * Created by RICO on 05.04.2015.
 */
public interface InterfaceRenderInventoryService {

    Resource getResource(int itemPointer);

    int getItemCount();
}
