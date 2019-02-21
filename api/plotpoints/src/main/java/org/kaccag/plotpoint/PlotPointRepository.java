package org.kaccag.plotpoint;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface that will automatically create basic CRUD methods.
 */
public interface PlotPointRepository extends MongoRepository<PlotPoint, Integer> {

    List<PlotPoint> findByUser(String user);
}
