package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PlotPointControllerTest {
    private PlotPointController controller;

    @Before
    public void setup() {
        PlotPointEntity plotPoint = new PlotPointEntity("user1", "summary1");

        PlotPointRepository mockRepo = Mockito.mock(PlotPointRepository.class);
        Mockito
                .when(mockRepo.save(plotPoint))
                .thenReturn(plotPoint);
        PlotPointService service = new PlotPointService(mockRepo);
        controller = new PlotPointController(service);
    }

    @Test
    public void createStatusCode() {
        ResponseEntity<PlotPointEntity> returned = controller.create(generatePlotPoint());

        Assert.assertEquals(HttpStatus.CREATED, returned.getStatusCode());
    }

    @Test
    public void updateStatusCode() {
        ResponseEntity<PlotPointEntity> returned = controller.update(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void deleteStatusCode() {
        ResponseEntity<PlotPointEntity> returned = controller.delete(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findOneStatusCode() {
        ResponseEntity<PlotPointEntity> returned = controller.findOne(123);

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findAllStatusCode() {
        ResponseEntity<List<PlotPointEntity>> returned = controller.findAll("user");

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }


    private PlotPointEntity generatePlotPoint() {
        return new PlotPointEntity("user", "summary");
    }
}
