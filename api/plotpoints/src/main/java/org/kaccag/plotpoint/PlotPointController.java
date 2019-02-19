package org.kaccag.plotpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "/plotpoint")
public class PlotPointController {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<PlotPoint> create(@RequestBody PlotPoint plotPoint) {
        return new ResponseEntity<>(plotPoint, HttpStatus.CREATED);
    }

    @PatchMapping(
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<PlotPoint> update(@RequestBody PlotPoint newPlotPoint) {
        return new ResponseEntity<>(newPlotPoint, HttpStatus.OK);
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<PlotPoint> delete(@RequestBody PlotPoint deletedTemplate) {
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
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<PlotPoint> findOne(@PathVariable int id) {
        return new ResponseEntity<>(
                new PlotPoint.Builder()
                        .setUser("user")
                        .setSummary("summary")
                        .build(),
                HttpStatus.OK);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<List<PlotPoint>> findAll(@PathVariable String user) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
