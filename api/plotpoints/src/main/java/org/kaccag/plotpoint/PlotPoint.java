package org.kaccag.plotpoint;

/**
 * Bean for plot points.
 * Requires user and summary.
 * Optionally takes a description.
 */
public class PlotPoint {

    private final int id;

    private final String user;

    private final String summary;

    private String description;

    private PlotPoint(final String user, final String summary, final String description) {
        String userSummary = user + summary;
        this.id = userSummary.hashCode();
        this.user = user;
        this.summary = summary;
        this.description = description;
    }

    private PlotPoint(final String user, final String summary) {
        this(user, summary, null);
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {
        private PlotPoint plotPoint;

        private String user;
        private String summary;

        public Builder() {
            this.plotPoint = new PlotPoint("", "");
        }

        public PlotPoint build() throws IllegalArgumentException {
            if (user == null || user.equals(""))
                throw new IllegalArgumentException("User is a required field for Plot Point");
            if (summary == null || summary.equals(""))
                throw new IllegalArgumentException("Summary is a required field for a Plot Point");

            PlotPoint builtPlotPoint;
            if (plotPoint.getDescription() == null || plotPoint.getDescription().equals("")) {
                builtPlotPoint = new PlotPoint(user, summary);
            } else {
                builtPlotPoint = new PlotPoint(user, summary, plotPoint.getDescription());
            }
            return builtPlotPoint;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setDescription(String description) {
            this.plotPoint.setDescription(description);
            return this;
        }
    }
}
