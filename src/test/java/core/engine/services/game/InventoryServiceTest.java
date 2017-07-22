package core.engine.services.game;

import core.engine.services.TranslateService;
import core.engine.services.setup.ItemService;
import core.game.ui.Inventory;
import core.helper.Config;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by RICO on 02.07.2017.
 */
public class InventoryServiceTest {


    private Logger logMock;

    private Config configMock;

    private TranslateService translateServiceMock;

    private ItemService itemServiceMock;

    private Inventory inventoryMock;

    @Before
    public void setup() {
        logMock = Mockito.mock(Logger.class);
        configMock = Mockito.mock(Config.class);
        translateServiceMock = Mockito.mock(TranslateService.class);
        itemServiceMock = Mockito.mock(ItemService.class);
        inventoryMock = Mockito.mock(Inventory.class);
    }


    @Test
    public void firstTest() {
        final InventoryService inventoryService = createService();

        when(inventoryMock.setInventoryService(inventoryService)).thenReturn(inventoryMock);

        assertThat(createService(), instanceOf(InventoryService.class));
    }

    private InventoryService createService() {
        return new InventoryService(logMock, configMock, translateServiceMock, itemServiceMock, inventoryMock);
    }

}
