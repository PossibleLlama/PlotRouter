package org.kaccag.plotpoint;

import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Bean for plot points.
 * Requires user and summary.
 * Optionally takes a description.
 */
public class PlotPointEntity {

    @Id
    private UUID id;

    private String user;

    private String summary;

    private String description;

    private UUID precedingPlotPointId;

    // Empty constructor for spring to do its magic
    PlotPointEntity() {
    }

    PlotPointEntity(final String user, final String summary) {
        this(user, summary, null);
    }

    PlotPointEntity(final String user, final String summary, final String description) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.summary = summary;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    void setUser(final String user) {
        this.user = user;
    }

    public String getSummary() {
        return summary;
    }

    void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    public UUID getPrecedingPlotPointId() {
        return precedingPlotPointId;
    }

    public void setPrecedingPlotPointId(UUID precedingPlotPointId) {
        this.precedingPlotPointId = precedingPlotPointId;
    }

    @Override
    public String toString() {
        return String.format(
                "Plotpoint[id=%s, user=%s, summary=%s, description=%s, prePlotpointId=%s]",
                id, user, summary, description, precedingPlotPointId
        );
    }
}
