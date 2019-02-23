package org.kaccag.plotpoint;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

public class PlotPointHelp {
    private final static String BASE_PATH = "/api/plotpoint";

    private final List<Resource> messages;

    public PlotPointHelp() {
        messages = Arrays.asList(
                new Resource(
                        HttpMethod.POST,
                        MediaType.APPLICATION_JSON,
                        BASE_PATH,
                        "Create a plot point object"),
                new Resource(
                        HttpMethod.PATCH,
                        MediaType.APPLICATION_JSON,
                        BASE_PATH,
                        "Update an existing plot point object"),
                new Resource(
                        HttpMethod.DELETE,
                        MediaType.APPLICATION_JSON,
                        BASE_PATH,
                        "Delete a plot point object"),
                new Resource(
                        HttpMethod.GET,
                        null,
                        BASE_PATH + "/id/{id}",
                        "Get a single plot point object by id"),
                new Resource(
                        HttpMethod.GET,
                        null,
                        BASE_PATH + "/user/{user}",
                        "Get all plot point objects by user")
        );
    }

    public List<Resource> getMessages() {
        return messages;
    }

    public class Resource {
        private final HttpMethod method;
        private final MediaType consumes;
        private final MediaType produces;
        private final String path;
        private final String description;

        public Resource(final HttpMethod method,
                        final MediaType consumes,
                        final String path,
                        final String description) {
            this.method = method;
            this.consumes = consumes;
            this.produces = MediaType.APPLICATION_JSON;
            this.path = path;
            this.description = description;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public MediaType getConsumes() {
            return consumes;
        }

        public MediaType getProduces() {
            return produces;
        }

        public String getPath() {
            return path;
        }

        public String getDescription() {
            return description;
        }
    }
}
