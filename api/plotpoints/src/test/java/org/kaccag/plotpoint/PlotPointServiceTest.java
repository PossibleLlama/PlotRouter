package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PlotPointServiceTest {
    private PlotPointService service;

    @Before
    public void setup() {
        PlotPointEntity plotPoint = new PlotPointEntity("user1", "summary1");

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.save(plotPoint))
                .thenReturn(plotPoint);
        service = new PlotPointService(mockRepo);
    }

    @Test
    public void insertValidPlotPoint() {
        PlotPointEntity returned = service.insert(generatePlotPoint());

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertEquals(null, returned.getDescription());
    }

    @Test
    public void insertNullUserPP() {
        PlotPointEntity inserted = new PlotPointEntity(null, "summary");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertEmptyUserPP() {
        PlotPointEntity inserted = new PlotPointEntity("", "summary");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertNullSummaryPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", null);

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertEmptySummaryPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertNullDescriptionPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", null);

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertEquals(null, returned.getDescription());
    }

    @Test
    public void insertEmptyDescriptionPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertEquals(null, returned.getDescription());
    }

    @Test
    public void insertContentDescriptionPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "description");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertEquals("description", returned.getDescription());
    }

    private PlotPointEntity generatePlotPoint() {
        return new PlotPointEntity("user1", "summary1");
    }
}
