package org.kaccag.plotpoint;

import org.junit.Assert;
import org.junit.Test;

public class PlotPointTest {
    private PlotPoint plotPoint1;
    private PlotPoint plotPoint2;

    private PlotPoint.Builder builder = new PlotPoint.Builder();

    @Test
    public void basicValidPlotPoint() {
        setupBaseBuilder();
        plotPoint1 = builder
                .build();

        // No error is thrown
    }

    @Test
    public void illegalArgBuildWithoutSettingUser() {
        try {
            builder
                    .setSummary("summary")
                    .build();
            Assert.fail("Error should be thrown if no user is set");
        } catch (IllegalArgumentException e) {
            // No user set
        }
    }

    @Test
    public void illegalArgBuildWithNullUser() {
        try {
            builder
                    .setSummary("summary")
                    .setUser(null)
                    .build();
            Assert.fail("Error should be thrown if user is set to null");
        } catch (IllegalArgumentException e) {
            // Null user set
        }
    }

    @Test
    public void illegalArgBuildWithEmptyUser() {
        try {
            builder
                    .setSummary("summary")
                    .setUser("")
                    .build();
            Assert.fail("Error should be thrown if user is empty string");
        } catch (IllegalArgumentException e) {
            // Empty user set
        }
    }

    @Test
    public void illegalArgBuildWithoutSettingSummary() {
        try {
            builder
                    .setUser("user")
                    .build();
            Assert.fail("Error should be thrown if summary is not set");
        } catch (IllegalArgumentException e) {
            // No summary set
        }
    }

    @Test
    public void illegalArgBuildWithNullSummary() {
        try {
            builder
                    .setUser("user")
                    .setSummary(null)
                    .build();
            Assert.fail("Error should be thrown if summary is null");
        } catch (IllegalArgumentException e) {
            // Null summary set
        }
    }

    @Test
    public void illegalArgBuildWithEmptySummary() {
        try {
            builder
                    .setUser("user")
                    .setSummary("")
                    .build();
            Assert.fail("Error should be thrown if summary is empty");
        } catch (IllegalArgumentException e) {
            // Empty summary set
        }
    }

    @Test
    public void descriptionNullWhenNotSet() {
        setupBaseBuilder();
        String desc = builder
                .build()
                .getDescription();
        Assert.assertEquals(null, desc);
    }

    @Test
    public void descriptionSetToNull() {
        setupBaseBuilder();
        String desc = builder
                .setDescription(null)
                .build()
                .getDescription();
        Assert.assertEquals(null, desc);
    }

    @Test
    public void descriptionSetToEmptyIsNull() {
        setupBaseBuilder();
        String desc = builder
                .setDescription("")
                .build()
                .getDescription();
        Assert.assertEquals(null, desc);
    }

    @Test
    public void uniqueIdCreated() {
        setupBaseBuilder();
        plotPoint1 = builder
                .setUser("usera")
                .build();

        plotPoint2 = builder
                .setUser("userb")
                .build();
        Assert.assertNotEquals(plotPoint1.getId(), plotPoint2.getId());
    }

    @Test
    public void setupTemplateWithNoFields() {
        plotPoint1 = builder.buildTemplate();

        Assert.assertEquals(0, plotPoint1.getId());
        Assert.assertEquals(null, plotPoint1.getUser());
        Assert.assertEquals(null, plotPoint1.getSummary());
        Assert.assertEquals(null, plotPoint1.getDescription());
    }

    private void setupBaseBuilder() {
        builder
                .setUser("user")
                .setSummary("summary");
    }
}
