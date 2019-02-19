package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PlotPointControllerTest {
    private PlotPointController controller = new PlotPointController();

    @Test
    public void createStatusCode() {
        ResponseEntity<PlotPoint> returned = controller.create(generatePlotPoint());

        Assert.assertEquals(HttpStatus.CREATED, returned.getStatusCode());
    }

    @Test
    public void updateStatusCode() {
        ResponseEntity<PlotPoint> returned = controller.update(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void deleteStatusCode() {
        ResponseEntity<PlotPoint> returned = controller.delete(generatePlotPoint());

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findOneStatusCode() {
        ResponseEntity<PlotPoint> returned = controller.findOne(123);

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }

    @Test
    public void findAllStatusCode() {
        ResponseEntity<List<PlotPoint>> returned = controller.findAll("user");

        Assert.assertEquals(HttpStatus.OK, returned.getStatusCode());
    }


    private PlotPoint generatePlotPoint() {
        return new PlotPoint.Builder()
                .setUser("user")
                .setSummary("summary")
                .build();
    }
}
