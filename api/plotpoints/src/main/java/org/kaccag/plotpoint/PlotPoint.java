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
        // TODO Change this to a method that can be done anywhere with the user and summary (in GUI)
        if (user != null && !user.equals("") && summary != null && !summary.equals(""))
            id = userSummary.hashCode();
        else
            id = 0;
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

    private void setDescription(final String description) {
        this.description = description;
    }

    public static class Builder {
        private PlotPoint plotPoint;

        private String user;
        private String summary;

        public Builder() {
            this.plotPoint = new PlotPoint("", "");
        }

        /**
         * Usual builder that is expected to be used.
         * User and Summary are mandatory fields for the plot point.
         *
         * @return
         * @throws IllegalArgumentException
         */
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

        /**
         * Build tool used for templates.
         * Expected to be used as a plot point search based on criteria listed within.
         * <p>
         * No mandatory fields.
         *
         * @return
         */
        PlotPoint buildTemplate() {
            return new PlotPoint(user, summary, plotPoint.getDescription());
        }

        public Builder setUser(final String user) {
            this.user = user;
            return this;
        }

        public Builder setSummary(final String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setDescription(final String description) {
            this.plotPoint.setDescription(description);
            return this;
        }
    }
}
