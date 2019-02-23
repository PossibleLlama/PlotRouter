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

    @Before
    public void setup() {
        PlotPointEntity plotPoint = generatePlotPoint();
        Optional<PlotPointEntity> foundPlotPoint = Optional.of(plotPoint);

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.save(plotPoint))
                .thenReturn(plotPoint);
        Mockito
                .when(mockRepo.findById(123))
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
        ResponseEntity<PlotPointEntity> returned =
                controller.update(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void deleteStatusCode() {
        ResponseEntity<PlotPointEntity> returned =
                controller.delete(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findOneStatusCode() {
        ResponseEntity<PlotPointEntity> returned =
                controller.findOne(123);

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
