package org.kaccag.plotpoint;

import com.mongodb.MongoClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

        checkForExistingPlotPoint(plotPoint);

        PlotPointEntity generatedId = new PlotPointEntity(
                plotPoint.getUser(),
                plotPoint.getSummary(),
                plotPoint.getDescription()
        );
        repo.insert(generatedId);
        LOGGER.info("Successfully created new plot point.");
        return generatedId;
    }

    public PlotPointEntity update(final PlotPointEntity template) {
        validateTemplate(template);
        PlotPointEntity foundPlotPoint = getById(template.getId());
        updateFoundWithValues(foundPlotPoint, template);
        if (template.getSummary() != null && !template.getSummary().equals(""))
            checkForExistingPlotPoint(foundPlotPoint);

        return repo.save(foundPlotPoint);
    }

    public PlotPointEntity delete(final UUID id) {
        PlotPointEntity deleted = getById(id);
        repo.deleteById(id);
        return deleted;
    }

    public List<PlotPointEntity> getAll() {
        return repo.findAll();
    }

    public PlotPointEntity getById(final UUID id) {
        Optional<PlotPointEntity> possiblePlotPoint = repo.findById(id);
        if (possiblePlotPoint.isPresent())
            return possiblePlotPoint.get();
        // TODO is this the most appropriate exception?
        throw new MongoClientException(
                String.format("No item of id '%s' can be found.", id.toString()));
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

    private void checkForExistingPlotPoint(PlotPointEntity plotPoint) {
        List<PlotPointEntity> foundSimilar = getByUser(plotPoint.getUser());

        // Remove all instances where the summary is different.
        foundSimilar.removeIf(
                element -> !element.getSummary().equals(plotPoint.getSummary())
        );

        // If there are matches, the user and summary are identical. These two together must be unique.
        if (foundSimilar.size() > 0)
            throw new IllegalArgumentException("Received plot point already exists");
    }

    private void validateTemplate(PlotPointEntity template) {
        if (template == null || template.getId() == null)
            throw new IllegalArgumentException("Received plot point object is malformed");
    }

    /**
     * Update values of what was found to that of templates.
     * This excludes user, as this should not change, and
     * id as it was used to find the non template instance.
     *
     * @param foundPlotPoint
     * @param template
     */
    private void updateFoundWithValues(PlotPointEntity foundPlotPoint, PlotPointEntity template) {
        // For each parameter, if it is asking to be changed, change it.
        if (template.getSummary() != null && !template.getSummary().equals(""))
            foundPlotPoint.setSummary(template.getSummary());
        if (template.getDescription() != null && !template.getDescription().equals(""))
            foundPlotPoint.setDescription(template.getDescription());
    }
}
