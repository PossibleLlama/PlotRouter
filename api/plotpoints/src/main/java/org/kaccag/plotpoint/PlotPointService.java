package org.kaccag.plotpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlotPointService {
    @Autowired
    private PlotPointRepository repo;

    PlotPointService(PlotPointRepository repo) {
        this.repo = repo;
    }

    public PlotPointEntity insert(final PlotPointEntity plotPoint) {
        if (!isPlotPointValid(plotPoint))
            throw new IllegalArgumentException("Received plot point object is malformed");
        repo.insert(plotPoint);
        return plotPoint;
    }

    private boolean isPlotPointValid(PlotPointEntity plotPoint) {
        if (plotPoint.getUser() == null || plotPoint.getUser().equals(""))
            return false;
        if (plotPoint.getSummary() == null || plotPoint.getSummary().equals(""))
            return false;
        if (plotPoint.getId() == 0)
            plotPoint.setId();
        if (plotPoint.getDescription() != null && plotPoint.getDescription().equals(""))
            plotPoint.setDescription(null);
        return true;
    }
}
