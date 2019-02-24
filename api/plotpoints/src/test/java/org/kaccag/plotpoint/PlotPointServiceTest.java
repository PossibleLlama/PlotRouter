package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kaccag.error.ResourceNotFoundException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;

public class PlotPointServiceTest {
    private PlotPointService service;

    private final UUID EXISTING_ID = UUID.randomUUID();
    private final UUID MISSING_ID = UUID.randomUUID();
    private final UUID SECOND_VALID_ID = UUID.randomUUID();

    @Before
    public void setup() {
        PlotPointEntity plotPoint = new PlotPointEntity(
                "user1", "summary1", "description1");
        plotPoint.setId(EXISTING_ID);
        PlotPointEntity plotPointWithPreceding = new PlotPointEntity(
                "user1", "summary2", "description1");
        plotPointWithPreceding.setId(SECOND_VALID_ID);
        plotPointWithPreceding.setPrecedingPlotPointId(EXISTING_ID);

        System.out.printf("********\nPlot point to emulate\n%s\n", plotPointWithPreceding.toString());

        Optional<PlotPointEntity> foundPlotPoint = Optional.of(plotPoint);
        Optional<PlotPointEntity> missingPlotPoint = Optional.empty();

        ArrayList<PlotPointEntity> foundByUser = new ArrayList<>();
        foundByUser.add(new PlotPointEntity("user2", "summary1"));
        foundByUser.add(new PlotPointEntity("user2", "summary2"));
        foundByUser.add(new PlotPointEntity("user2", "summary3"));

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.insert(plotPoint))
                .thenReturn(plotPoint);
        Mockito
                .when(mockRepo.insert(plotPointWithPreceding))
                .thenReturn(plotPointWithPreceding);
        Mockito
                .when(mockRepo.findByUser("user1"))
                .thenReturn(new ArrayList<>());
        Mockito
                .when(mockRepo.findByUser("user2"))
                .thenReturn(foundByUser);
        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID)
                ))
                .thenReturn(plotPoint);
        Mockito
                .when(mockRepo.findById(EXISTING_ID))
                .thenReturn(foundPlotPoint);
        Mockito
                .when(mockRepo.findById(MISSING_ID))
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
    public void insertNullUserPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity(null, "summary");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertEmptyUserPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("", "summary");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertNullSummaryPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("user", null);

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertEmptySummaryPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("user", "");

        try {
            service.insert(inserted);
            Assert.fail("Should throw illegal argument exception");
        } catch (IllegalArgumentException e) {
            // Illegal arg exception thrown
        }
    }

    @Test
    public void insertNullDescriptionPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", null);

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertEmptyDescriptionPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertContentDescriptionPlotPoint() {
        PlotPointEntity inserted = new PlotPointEntity("user", "summary", "description");

        PlotPointEntity returned = service.insert(inserted);

        Assert.assertEquals("user", returned.getUser());
        Assert.assertEquals("summary", returned.getSummary());
        Assert.assertEquals("description", returned.getDescription());
    }

    @Test
    public void insertWithNoExistingPlotPointsByUser() {
        PlotPointEntity returned = service.insert(generatePlotPoint());

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertWithDifferentPlotPointsByUser() {
        PlotPointEntity sameUserDifferentSummary =
                new PlotPointEntity("user2", "new summary");
        PlotPointEntity returned = service.insert(sameUserDifferentSummary);

        Assert.assertEquals("user2", returned.getUser());
        Assert.assertEquals("new summary", returned.getSummary());
        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void insertWithExistingPlotPointSummaryByUser() {
        PlotPointEntity sameUserSameSummary =
                new PlotPointEntity("user2", "summary1");

        try {
            service.insert(sameUserSameSummary);
            Assert.fail("Should have thrown exception as user summary combo already exist.");
        } catch (IllegalArgumentException e) {
            // Expected error to be thrown
        }
    }

    @Test
    public void insertWithValidPrePlotPoint() {
        PlotPointEntity plotPointWithPrePlotPoint = new PlotPointEntity(
                "user1", "summary2", "description1"
        );
        plotPointWithPrePlotPoint.setPrecedingPlotPointId(EXISTING_ID);

        PlotPointEntity returned = service.insert(plotPointWithPrePlotPoint);

        System.out.printf("********\nPlot point sent\n%s\nPlot point returned\n%s\n", plotPointWithPrePlotPoint.toString(), returned.toString());
        Assert.assertEquals(EXISTING_ID, returned.getPrecedingPlotPointId());
    }

    @Test
    public void updateWithNullTemplate() {
        try {
            service.update(null);
            Assert.fail("Error should be thrown if template is null");
        } catch (IllegalArgumentException e) {
            // Expected error thrown
        }
    }

    @Test
    public void updateWithNoProvidedId() {
        PlotPointEntity noId = new PlotPointEntity();
        noId.setUser("user");
        noId.setSummary("summary");

        try {
            service.update(noId);
            Assert.fail("Error should be thrown if no id is provided");
        } catch (IllegalArgumentException e) {
            // Expected error to be thrown
        }
    }

    @Test
    public void updateUserDoesNothing() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setUser("new user");

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getUser(), returned.getUser());
    }

    @Test
    public void updateSummary() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary("new summary");

        PlotPointEntity returned = service.update(template);

        Assert.assertEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateNullSummaryDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary(null);

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateEmptySummaryDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary("");

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateNullDescriptionDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setDescription(null);

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getDescription(), returned.getDescription());
    }

    @Test
    public void updateEmptyDescriptionDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setDescription("");

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getDescription(), returned.getDescription());
    }

    @Test
    public void getByValidId() {
        PlotPointEntity returned = service.getById(EXISTING_ID);

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertEquals("description1", returned.getDescription());
    }

    @Test
    public void getByMissingId() {
        try {
            service.getById(MISSING_ID);
            Assert.fail("Error should be thrown by missng id.");
        } catch (ResourceNotFoundException e) {
            // Expected error thrown
        }
    }

    private PlotPointEntity generatePlotPoint() {
        return new PlotPointEntity("user1", "summary1");
    }
}
