package core.engine.services.game;

import core.engine.services.TranslateService;
import core.engine.services.setup.ItemService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.game.ui.Inventory;
import core.game.unit.Player;
import core.helper.Config;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    public void setup() {
        logMock = mock(Logger.class);
        configMock = mock(Config.class);
        translateServiceMock = mock(TranslateService.class);
        itemServiceMock = mock(ItemService.class);
        inventoryMock = mock(Inventory.class);
    }

    @Test
    public void getUiSuccess() {
        assertEquals(createService().getUi(), inventoryMock);
    }

    @Test
    public void updateSuccess() {
        final Map<ResourcesType, Integer> backpackStub = new HashMap<>();
        backpackStub.put(ResourcesType.GOLD, 20);
        backpackStub.put(ResourcesType.WOOD, 40);

        final Player playerStub = mock(Player.class);
        when(playerStub.getBackpack()).thenReturn(backpackStub);

        final InventoryService inventoryService = createService();
        inventoryService.update(playerStub);

        assertEquals(2, inventoryService.getItemCount());
    }

    @Test
    public void getResourceSuccess() {
        final Map<ResourcesType, Integer> backpackStub = new HashMap<>();
        backpackStub.put(ResourcesType.GOLD, 20);
        backpackStub.put(ResourcesType.WOOD, 40);
        backpackStub.put(ResourcesType.FOOD, 10);

        final Player playerStub = mock(Player.class);
        when(playerStub.getBackpack()).thenReturn(backpackStub);

        final InventoryService inventoryService = createService();
        inventoryService.update(playerStub);

        final Resource resourceGoldStub = mock(Resource.class);
        final Resource resourceWoodStub = mock(Resource.class);
        final Resource resourceFoodStub = mock(Resource.class);

        when(itemServiceMock.getItem(argThat(argument -> {
            if (argument.equals(ResourcesType.GOLD)) {
                return true;
            }
            if (argument.equals(ResourcesType.WOOD)) {
                return true;
            }
            if (argument.equals(ResourcesType.FOOD)) {
                return true;
            }
            return false;
        }))).thenReturn(resourceGoldStub, resourceWoodStub, resourceFoodStub, resourceGoldStub, resourceGoldStub);

        assertEquals(resourceGoldStub, inventoryService.getResource(0), "0 Item should be Gold");
        assertEquals(resourceWoodStub, inventoryService.getResource(1), "1 Item should be Wood");
        assertEquals(resourceFoodStub, inventoryService.getResource(2), "2 Item should be Food");
        assertEquals(resourceGoldStub, inventoryService.getResource(3), "3 Item should be Gold");
        assertEquals(resourceGoldStub, inventoryService.getResource(-1), "-1 Item should be Gold");
    }

    private InventoryService createService() {
        return new InventoryService(logMock, configMock, translateServiceMock, itemServiceMock, inventoryMock);
    }

}
