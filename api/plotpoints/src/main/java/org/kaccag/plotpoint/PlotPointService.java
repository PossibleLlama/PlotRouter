package org.kaccag.plotpoint;

import com.mongodb.MongoClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PlotPointService {
    private static final Logger LOGGER = Logger.getLogger(
            PlotPointService.class.getSimpleName());

    @Autowired
    private PlotPointRepository repo;

    PlotPointService(PlotPointRepository repo) {
        this.repo = repo;
    }

    public PlotPointEntity insert(final PlotPointEntity plotPoint) {
        if (!isPlotPointValid(plotPoint))
            throw new IllegalArgumentException("Received plot point object is malformed");

        List<PlotPointEntity> foundSimilar = getByUser(plotPoint.getUser());
        // Remove all intances where the summary is different.
        foundSimilar.removeIf(
                element -> !element.getSummary().equals(plotPoint.getSummary())
        );

        // If there are matches, the user and summary are identical. These two together must be unique.
        if (foundSimilar.size() > 0)
            throw new IllegalArgumentException("Received plot point already exists");

        PlotPointEntity generatedId = new PlotPointEntity(
                plotPoint.getUser(),
                plotPoint.getSummary(),
                plotPoint.getDescription()
        );
        repo.insert(generatedId);
        LOGGER.info("Successfully created new plot point.");
        return generatedId;
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

    public List<PlotPointEntity> getByUser(final String user) {
        return repo.findByUser(user);
    }

    private boolean isPlotPointValid(PlotPointEntity plotPoint) {
        if (plotPoint.getUser() == null || plotPoint.getUser().equals(""))
            return false;
        if (plotPoint.getSummary() == null || plotPoint.getSummary().equals(""))
            return false;
        if (plotPoint.getDescription() != null && plotPoint.getDescription().equals(""))
            plotPoint.setDescription(null);
        return true;
    }
}
