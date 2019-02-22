package org.kaccag.plotpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Any errors thrown should be directed to the error controller.
 * TODO create an error controller
 */
@RestController(value = "/api/plotpoint")
public class PlotPointApiController {
    @Autowired
    private PlotPointService service;

    public PlotPointApiController(final PlotPointService service) {
        this.service = service;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointHelp> getHelp() {
        PlotPointHelp helper = new PlotPointHelp();
        return new ResponseEntity<>(helper, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> create(@RequestBody PlotPointEntity plotPoint) {
        service.insert(plotPoint);
        return new ResponseEntity<>(plotPoint, HttpStatus.CREATED);
    }

    @PatchMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> update(@RequestBody PlotPointEntity newPlotPoint) {
        return new ResponseEntity<>(newPlotPoint, HttpStatus.OK);
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> delete(@RequestBody PlotPointEntity deletedTemplate) {
        return new ResponseEntity<>(deletedTemplate, HttpStatus.OK);
    }

    /**
     * Expected that a calling client will perform calculation
     * for generation of id based on user and summary.
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> findOne(@PathVariable int id) {
        return new ResponseEntity<>(
                new PlotPointEntity("user", "summary"),
                HttpStatus.OK);
    }

    /**
     * Searching for all plot points that are from a single user.
     * User may be a token or similar in the future.
     *
     * @param user
     * @return
     */
    @GetMapping(
            value = "/user/{user}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PlotPointEntity>> findAll(@PathVariable String user) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
