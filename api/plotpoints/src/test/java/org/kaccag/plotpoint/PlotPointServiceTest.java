package org.kaccag.plotpoint;

import com.mongodb.MongoClientException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class PlotPointServiceTest {
    private PlotPointService service;

    @Before
    public void setup() {
        PlotPointEntity plotPoint = new PlotPointEntity("user1", "summary1");
        Optional<PlotPointEntity> foundPlotPoint = Optional.of(plotPoint);
        Optional<PlotPointEntity> missingPlotPoint = Optional.empty();

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.save(plotPoint))
                .thenReturn(plotPoint);
        Mockito
                .when(mockRepo.findById(1))
                .thenReturn(foundPlotPoint);
        Mockito
                .when(mockRepo.findById(2))
                .thenReturn(missingPlotPoint);
        service = new PlotPointService(mockRepo);
    }

    @Test
    public void insertValidPlotPoint() {
        PlotPointEntity returned = service.insert(generatePlotPoint());

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertNull(returned.getDescription());
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
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertEmptyDescriptionPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertContentDescriptionPP() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "description");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertEquals("description", returned.getDescription());
    }

    @Test
    public void getByValidId() {
        PlotPointEntity returned = service.getById(1);

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void getByMissingId() {
        try {
            service.getById(2);
            Assert.fail("Error should be thrown by missng id.");
        } catch (MongoClientException e) {
            // Expected error thrown
        }
    }

    @Test
    public void updateWithNullTemplate() {
        try {
            service.update(null);
            Assert.fail("Error should be thrown if template is null.");
        } catch (IllegalArgumentException e) {
            // Expected error thrown
        }
    }

    private PlotPointEntity generatePlotPoint() {
        return new PlotPointEntity("user1", "summary1");
    }
}
