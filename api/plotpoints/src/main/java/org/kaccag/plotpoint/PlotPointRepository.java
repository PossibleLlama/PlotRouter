package org.kaccag.plotpoint;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Interface that will automatically create basic CRUD methods.
 */
public interface PlotPointRepository extends MongoRepository<PlotPointEntity, UUID> {

    List<PlotPointEntity> findByUser(String user);
}
