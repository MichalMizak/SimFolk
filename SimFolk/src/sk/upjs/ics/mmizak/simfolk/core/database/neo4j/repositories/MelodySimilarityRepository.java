package sk.upjs.ics.mmizak.simfolk.core.database.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import sk.upjs.ics.mmizak.simfolk.core.database.neo4j.entities.NeoSimilarity;

@Repository
public interface MelodySimilarityRepository extends Neo4jRepository<NeoSimilarity, Long> {

}
