package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class PlotPointApiControllerTest {
    private PlotPointApiController controller;

    private static final int SUCCESS_ID = 123;

    @Before
    public void setup() {
        PlotPointEntity plotPoint = generatePlotPoint();
        plotPoint.setId(SUCCESS_ID);
        Optional<PlotPointEntity> foundPlotPoint = Optional.of(plotPoint);

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.save(plotPoint))
                .thenReturn(plotPoint);
        Mockito
                .when(mockRepo.findById(SUCCESS_ID))
                .thenReturn(foundPlotPoint);
        PlotPointService service = new PlotPointService(mockRepo);
        controller = new PlotPointApiController(service);
    }

    @Test
    public void createStatusCode() {
        ResponseEntity<PlotPointEntity> returned =
                controller.create(generatePlotPoint());

        Assert.assertEquals(HttpStatus.CREATED, returned.getStatusCode());
    }

    @Test
    public void updateStatusCode() {
        PlotPointEntity updateTo = generatePlotPoint();
        updateTo.setId(SUCCESS_ID);
        ResponseEntity<PlotPointEntity> returned =
                controller.update(updateTo);

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void deleteStatusCode() {
        ResponseEntity<PlotPointEntity> returned =
                controller.delete(SUCCESS_ID);

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findOneStatusCode() {
        ResponseEntity<PlotPointEntity> returned =
                controller.findOne(SUCCESS_ID);

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findAllUserStatusCode() {
        ResponseEntity<List<PlotPointEntity>> returned =
                controller.findAllByUser("user");

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findAllStatusCode() {
        ResponseEntity<List<PlotPointEntity>> returned =
                controller.findAll();

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void getHelpStatusCode() {
        ResponseEntity<PlotPointHelp> returned =
                controller.getHelp();

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    private PlotPointEntity generatePlotPoint() {
        return new PlotPointEntity("user", "summary");
    }
}
