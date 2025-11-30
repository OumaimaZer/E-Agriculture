package estm.dut.eagri.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import estm.dut.eagri.model.Plant.Plants;

public interface PlantRepository extends MongoRepository<Plants, String> {
    List<Plants> findAll();

    List<Plants> findBySeason(String season);

    List<Plants> findBySector(String sector);

    List<Plants> findBySeasonAndSector(String season, String sector);

    Optional<Plants> findById(String id);

    Plants findByName(String name);
}