package org.kaccag.plotpoint;

import java.util.List;

public class DisplayedPlotPointsList {
    private List<PlotPointEntity> plotPoints;

    public DisplayedPlotPointsList(final List<PlotPointEntity> list) {
        this.plotPoints = list;
    }

    public List<PlotPointEntity> getPlotPoints() {
        return plotPoints;
    }
}
