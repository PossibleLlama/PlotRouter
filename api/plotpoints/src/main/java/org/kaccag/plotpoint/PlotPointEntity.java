package org.kaccag.plotpoint;

import org.springframework.data.annotation.Id;

/**
 * Bean for plot points.
 * Requires user and summary.
 * Optionally takes a description.
 */
public class PlotPointEntity {

    @Id
    private int id;

    private String user;

    private String summary;

    private String description;

    // Empty constructor for spring to do its magic
    PlotPointEntity() {
    }

    PlotPointEntity(final String user, final String summary) {
        this(user, summary, null);
    }

    PlotPointEntity(final String user, final String summary, final String description) {
        // TODO Change generate id to a method that can be done anywhere with the user and summary (in GUI)
        this.user = user;
        this.summary = summary;
        this.description = description;
        this.setId(0);
    }

    public int getId() {
        return id;
    }

    /**
     * Set id should only be done once the user and summary are set.
     *
     * @param id
     */
    void setId(final int id) {
        if (isUserAndSummaryFilled()) {
            String userSummary = this.user + this.summary;
            this.id = userSummary.hashCode();
        } else {
            this.id = id;
        }
    }

    void setId() {
        if (isUserAndSummaryFilled()) {
            String userSummary = this.user + this.summary;
            this.id = userSummary.hashCode();
        }
    }

    public String getUser() {
        return user;
    }

    void setUser(final String user) {
        this.user = user;
        setId(0);
    }

    public String getSummary() {
        return summary;
    }

    void setSummary(final String summary) {
        this.summary = summary;
        setId(0);
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Plotpoint[id=%s, user=%s, summary=%s, description=%s]",
                id, user, summary, description
        );
    }

    /**
     * Check if user and summary are both not null or empty.
     * @return
     */
    private boolean isUserAndSummaryFilled() {
        return user != null && !user.equals("")
                && summary != null && !summary.equals("");
    }
}
