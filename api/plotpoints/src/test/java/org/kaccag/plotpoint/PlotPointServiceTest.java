package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kaccag.error.ResourceNotFoundException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

public class PlotPointServiceTest {
    private final UUID EXISTING_ID = UUID.randomUUID();
    private final UUID MISSING_ID = UUID.randomUUID();
    private final UUID SECOND_VALID_ID = UUID.randomUUID();
    private final UUID VALID_ID_DEPENDENT_PREPP = UUID.randomUUID();

    PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
    private PlotPointService service;

    @Before
    public void setup() {
        PlotPointEntity plotPoint = generatePlotPointWithDescription("summary1", EXISTING_ID);
        PlotPointEntity plotPointWithPreceding = generatePlotPointWithDescription("summary2", SECOND_VALID_ID);
        plotPointWithPreceding.setPrecedingPlotPointId(EXISTING_ID);

        Optional<PlotPointEntity> foundPlotPoint = Optional.of(plotPoint);
        Optional<PlotPointEntity> missingPlotPoint = Optional.empty();

        ArrayList<PlotPointEntity> foundByUser = new ArrayList<>();
        PlotPointEntity foundByUser1 = new PlotPointEntity("user2", "summary1");
        foundByUser1.setId(VALID_ID_DEPENDENT_PREPP);
        PlotPointEntity foundByUser2 = new PlotPointEntity("user2", "summary2");
        foundByUser2.setPrecedingPlotPointId(VALID_ID_DEPENDENT_PREPP);
        foundByUser.add(foundByUser1);
        foundByUser.add(foundByUser2);
        foundByUser.add(new PlotPointEntity("user2", "summary3"));

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
                .when(mockRepo.findById(EXISTING_ID))
                .thenReturn(foundPlotPoint);
        Mockito
                .when(mockRepo.findById(MISSING_ID))
                .thenReturn(missingPlotPoint);
        Mockito
                .when(mockRepo.findById(SECOND_VALID_ID))
                .thenReturn(Optional.of(plotPointWithPreceding));
        Mockito
                .when(mockRepo.findById(VALID_ID_DEPENDENT_PREPP))
                .thenReturn(Optional.of(foundByUser1));
        Mockito
                .when(mockRepo.findById(foundByUser2.getId()))
                .thenReturn(Optional.of(foundByUser2));
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
    public void insertNullId() {
        PlotPointEntity template = new PlotPointEntity();
        template.setUser("user1");
        template.setSummary("summary1");

        Mockito
                .when(mockRepo.insert(
                        any(PlotPointEntity.class)
                ))
                .thenReturn(generatePlotPoint());

        PlotPointEntity returned = service.insert(template);

        Assert.assertNotNull(returned.getId());
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

        Assert.assertEquals(EXISTING_ID, returned.getPrecedingPlotPointId());
    }

    @Test
    public void insertWithInvalidPrePlotPointId() {
        PlotPointEntity plotPoint = new PlotPointEntity(
                "user1", "summary2", "description1"
        );
        plotPoint.setPrecedingPlotPointId(MISSING_ID);

        try {
            service.insert(plotPoint);
            Assert.fail("Preceding plot point of missing id should throw exception");
        } catch (ResourceNotFoundException e) {
            // Expected error thrown
        }
    }

    @Test
    public void insertWithDifferentPrePlotPointUser() {
        PlotPointEntity plotPoint = new PlotPointEntity(
                "invalidUser", "summary2", "description1"
        );
        plotPoint.setPrecedingPlotPointId(EXISTING_ID);

        try {
            service.insert(plotPoint);
            Assert.fail("Preceding plot point by different user should throw exception");
        } catch (IllegalArgumentException e) {
            // Expected error thrown
        }
    }

    @Test
    public void insertWithSameIdAndPrePlotPointId() {
        PlotPointEntity plotPoint = new PlotPointEntity(
                "user1", "summary2", "description1"
        );
        plotPoint.setId(EXISTING_ID);
        plotPoint.setPrecedingPlotPointId(EXISTING_ID);

        try {
            service.insert(plotPoint);
            Assert.fail("Should throw error if provided id and pre plot point id are the same");
        } catch (IllegalArgumentException e) {
            // Expected exception thrown.
        }
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

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID)
                ))
                .thenReturn(generatePlotPointWithDescription("summary1", EXISTING_ID));

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getUser(), returned.getUser());
    }

    @Test
    public void updateSummary() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary("new summary");

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance ->
                                plotPointInstance.getId() == EXISTING_ID &&
                                        plotPointInstance.getSummary().equals("new summary"))
                ))
                .thenReturn(generatePlotPointWithDescription("new summary", EXISTING_ID));

        PlotPointEntity returned = service.update(template);

        Assert.assertEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateNullSummaryDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary(null);

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID)
                ))
                .thenReturn(generatePlotPointWithDescription("summary1", EXISTING_ID));

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateEmptySummaryDoesNothingToValue() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setSummary("");

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID)
                ))
                .thenReturn(generatePlotPointWithDescription("summary1", EXISTING_ID));

        PlotPointEntity returned = service.update(template);

        Assert.assertNotEquals(template.getSummary(), returned.getSummary());
    }

    @Test
    public void updateNullDescription() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setDescription(null);

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID
                                && plotPointInstance.getDescription() == null)
                ))
                .thenReturn(template);

        PlotPointEntity returned = service.update(template);

        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void updateToEmptyDescriptionReturnsNull() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setDescription("");

        PlotPointEntity mockedPP = generatePlotPointWithDescription("summary1", EXISTING_ID);
        mockedPP.setDescription(null);

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance ->
                                plotPointInstance.getId() == EXISTING_ID &&
                                        plotPointInstance.getDescription() == null)
                ))
                .thenReturn(mockedPP);

        PlotPointEntity returned = service.update(template);

        Assert.assertNull(returned.getDescription());
    }

    @Test
    public void updateToNullPrePlotPoint() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(SECOND_VALID_ID);
        template.setPrecedingPlotPointId(null);

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == SECOND_VALID_ID)
                ))
                .thenReturn(template);

        PlotPointEntity returned = service.update(template);

        Assert.assertNull(returned.getPrecedingPlotPointId());
    }

    @Test
    public void updateFromNullPrePlotPoint() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setPrecedingPlotPointId(SECOND_VALID_ID);

        Mockito
                .when(mockRepo.save(
                        argThat(plotPointInstance -> plotPointInstance.getId() == EXISTING_ID &&
                                plotPointInstance.getPrecedingPlotPointId() == SECOND_VALID_ID)
                ))
                .thenReturn(template);

        PlotPointEntity returned = service.update(template);

        Assert.assertEquals(SECOND_VALID_ID, returned.getPrecedingPlotPointId());
    }

    @Test
    public void updatePrePlotPointSameAsId() {
        PlotPointEntity template = new PlotPointEntity();
        template.setId(EXISTING_ID);
        template.setPrecedingPlotPointId(EXISTING_ID);

        try {
            service.update(template);
            Assert.fail("Cannot have same id for object and in prePP field");
        } catch (IllegalArgumentException e) {
            // Expected exception thrown.
        }
    }

    @Test
    public void deleteValidId() {
        PlotPointEntity returned = service.delete(EXISTING_ID);

        Assert.assertEquals("user1", returned.getUser());
        Assert.assertEquals("summary1", returned.getSummary());
        Assert.assertEquals("description1", returned.getDescription());
    }

    @Test
    public void deleteMissingId() {
        try {
            service.delete(MISSING_ID);
            Assert.fail("Deleting a missing id should throw an error");
        } catch (ResourceNotFoundException e) {
            // Expected error thrown
        }
    }

    @Test
    public void deletePlotPointWithAnotherDependentOnIt() {
        service.delete(VALID_ID_DEPENDENT_PREPP);

        // This is actually tested in automated testing. As mock would need to return
        // a different list the second call, this just checks no errors are thrown.
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

    private PlotPointEntity generatePlotPointWithDescription(String summary, UUID id) {
        PlotPointEntity plotPoint = new PlotPointEntity(
                "user1", summary, "description1");
        plotPoint.setId(id);
        return plotPoint;
    }
}
