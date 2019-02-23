package org.kaccag.plotpoint;

import com.mongodb.MongoClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<PlotPointEntity> getAll() {
        return repo.findAll();
    }

    public PlotPointEntity getById(final int id) {
        Optional<PlotPointEntity> possiblePlotPoint = repo.findById(id);
        if (possiblePlotPoint.isPresent())
            return possiblePlotPoint.get();
        // TODO is this the most appropriate exception?
        throw new MongoClientException(
                String.format("No item of id '%d' can be found.", id));
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
