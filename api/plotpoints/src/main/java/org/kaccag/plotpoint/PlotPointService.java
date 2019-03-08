package org.kaccag.plotpoint;

import org.kaccag.error.ResourceNotFoundException;
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
        if (!isPlotPointFunctionallyValid(plotPoint)) {
            LOGGER.warning(String.format(
                    "Creation of plot point halted due to it being malformed. %s",
                    plotPoint.toString()));
            throw new IllegalArgumentException("Received plot point object is malformed");
        }
        PlotPointEntity generatedId = new PlotPointEntity(
                plotPoint.getUser(),
                plotPoint.getSummary(),
                plotPoint.getDescription()
        );
        checkForExistingPlotPoint(generatedId);
        validatePrecedingPlotPoint(plotPoint, generatedId);

        repo.insert(generatedId);
        LOGGER.info("Successfully created new plot point");
        return generatedId;
    }

    public PlotPointEntity update(final PlotPointEntity toBe) {
        validateTemplate(toBe);
        PlotPointEntity current = getById(toBe.getId());
        updateFoundWithValues(current, toBe);
        if (toBe.getSummary() != null && !toBe.getSummary().equals(""))
            checkForExistingPlotPoint(current);

        LOGGER.info(String.format("Updating plot point %s",
                current.getId().toString()));
        return repo.save(current);
    }

    public PlotPointEntity delete(final UUID id) {
        PlotPointEntity deleted = getById(id);
        repo.deleteById(id);

        List<PlotPointEntity> usersPP = getByUser(deleted.getUser());
        for (PlotPointEntity entry : usersPP) {
            if (entry.getPrecedingPlotPointId() != null && entry.getPrecedingPlotPointId().equals(id)) {
                PlotPointEntity ppToBeUpdated = new PlotPointEntity();
                ppToBeUpdated.setId(entry.getId());
                // Update to deleted preceding plot point
                ppToBeUpdated.setPrecedingPlotPointId(deleted.getPrecedingPlotPointId());

                update(ppToBeUpdated);
            }
        }

        LOGGER.info(String.format("Removed plot point %s by %s",
                deleted.getId().toString(), deleted.getUser()));
        return deleted;
    }

    public List<PlotPointEntity> getAll() {
        return repo.findAll();
    }

    public PlotPointEntity getById(final UUID id) {
        Optional<PlotPointEntity> possiblePlotPoint = repo.findById(id);
        if (possiblePlotPoint.isPresent())
            return possiblePlotPoint.get();
        LOGGER.warning(String.format("Plot point %s could not be found", id.toString()));
        throw new ResourceNotFoundException(
                String.format("No item of id '%s' can be found", id.toString()));
    }

    public List<PlotPointEntity> getByUser(final String user) {
        return repo.findByUser(user);
    }

    private boolean isPlotPointFunctionallyValid(PlotPointEntity plotPoint) {
        if (plotPoint.getUser() == null || plotPoint.getUser().equals("")) {
            LOGGER.warning(String.format("Invalid user '%s' provided", plotPoint.getUser()));
            return false;
        }
        if (plotPoint.getSummary() == null || plotPoint.getSummary().equals("")) {
            LOGGER.warning(String.format("Invalid summary '%s' provided", plotPoint.getSummary()));
            return false;
        }
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
        if (foundSimilar.size() > 0) {
            LOGGER.warning(String.format(
                    "Plot point by user %s with summary %s already exist",
                    plotPoint.getUser(), plotPoint.getSummary()));
            throw new IllegalArgumentException("Received plot point already exists");
        }
    }

    private void validatePrecedingPlotPoint(PlotPointEntity passedPlotPoint, PlotPointEntity endPlotPoint) {
        if (passedPlotPoint.getPrecedingPlotPointId() != null) {
            try {
                if (passedPlotPoint.getId().equals(passedPlotPoint.getPrecedingPlotPointId()))
                    throw new IllegalArgumentException(
                            "Plot point cannot be preceded by itself");
            } catch (NullPointerException e) {
                if (endPlotPoint.getId().equals(passedPlotPoint.getPrecedingPlotPointId()))
                    throw new IllegalArgumentException(
                            "Plot point cannot be preceded by itself");
            }
            try {
                PlotPointEntity preceding = getById(passedPlotPoint.getPrecedingPlotPointId());
                if (!preceding.getUser().equals(endPlotPoint.getUser()))
                    throw new IllegalArgumentException(
                            "Provided preceding plot point is not by the same user");
                endPlotPoint.setPrecedingPlotPointId(preceding.getId());
            } catch (ResourceNotFoundException e) {
                throw new ResourceNotFoundException("Provided preceding plot point does not exist", e);
            }
        }
    }

    private void validateTemplate(PlotPointEntity template) {
        if (template == null || template.getId() == null) {
            LOGGER.warning("Plot point or id is null");
            throw new IllegalArgumentException("Received plot point object is malformed");
        }
    }

    /**
     * Update values of what was found to that of templates.
     * This excludes user, as this should not change, and
     * id as it was used to find the non template instance.
     *
     * @param currentPlotPoint
     * @param template
     */
    private void updateFoundWithValues(PlotPointEntity currentPlotPoint, PlotPointEntity template) {
        // TODO whitelist, not blacklist. Return a new Object
        // For each parameter, if it is asking to be changed, change it.
        if (template.getSummary() != null && !template.getSummary().equals(""))
            currentPlotPoint.setSummary(template.getSummary());
        if (currentPlotPoint.getDescription() != template.getDescription()) {
            if (template.getDescription() == null)
                currentPlotPoint.setDescription(null);
            else {
                if (template.getDescription().equals(""))
                    template.setDescription(null);
                currentPlotPoint.setDescription(template.getDescription());
            }
        }
        if (currentPlotPoint.getPrecedingPlotPointId() != null
                || template.getPrecedingPlotPointId() != null) {
            try {
                if (!currentPlotPoint.getPrecedingPlotPointId().equals(template.getPrecedingPlotPointId()))
                    if (template.getPrecedingPlotPointId() == null)
                        currentPlotPoint.setPrecedingPlotPointId(null);
            } catch (NullPointerException e) {
                // We know that either current or template is not null, and one is.
                // If null pointer is thrown, current must be null.
                currentPlotPoint.setPrecedingPlotPointId(template.getPrecedingPlotPointId());
            }
            validatePrecedingPlotPoint(template, currentPlotPoint);
        }
    }
}
