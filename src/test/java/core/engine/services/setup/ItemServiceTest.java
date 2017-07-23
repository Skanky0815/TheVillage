package core.engine.services.setup;

import core.engine.services.XMLReaderService;
import core.game.item.Resource;
import core.game.item.ResourcesType;
import core.helper.Config;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    private Logger logMock;

    private Config configMock;

    private XMLReaderService xmlReaderService;

    @Before
    public void setup() {
        logMock = mock(Logger.class);
        configMock = mock(Config.class);
        xmlReaderService = mock(XMLReaderService.class);
    }

    @Test
    public void getItemSuccess() {
        final ArrayList<Object> resourceList = new ArrayList<>();

        final Resource resourceBlackPowderStub = mock(Resource.class);
        when(resourceBlackPowderStub.getType()).thenReturn(ResourcesType.BLACK_POWDER);
        resourceList.add(resourceBlackPowderStub);

        final Resource resourceGoldStub = mock(Resource.class);
        when(resourceGoldStub.getType()).thenReturn(ResourcesType.GOLD);
        resourceList.add(resourceGoldStub);

        when(xmlReaderService.loadAllXml(eq("items/resources/"), eq(Resource.class))).thenReturn(resourceList);

        final ItemService itemService = createService();

        assertEquals(resourceBlackPowderStub, itemService.getItem(ResourcesType.BLACK_POWDER));
        assertEquals(resourceGoldStub, itemService.getItem(ResourcesType.GOLD));
    }

    @Test
    public void getItemNull() {
        doNothing().when(logMock).debug("Not item found for BLACK_POWDER");

        final ItemService itemService = createService();
        assertNull(itemService.getItem(ResourcesType.BLACK_POWDER));
    }

    private ItemService createService() {
        return new ItemService(logMock, configMock, xmlReaderService);
    }
}
