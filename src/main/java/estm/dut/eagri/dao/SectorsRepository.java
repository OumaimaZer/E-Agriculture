package estm.dut.eagri.dao;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estm.dut.eagri.model.Plant.Sectors;

@Repository
@Document(collection = "Sectors")
public interface SectorsRepository extends MongoRepository<Sectors, String> {
    List<Sectors> findAll();

    Sectors findByName(String name);
}