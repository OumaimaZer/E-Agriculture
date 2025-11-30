package estm.dut.eagri.dao;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import estm.dut.eagri.model.Regions;

@Document(collection = "Regions")
public interface RegionsRepository extends MongoRepository<Regions, String> {
    List<Regions> findAll();

    Regions findByName(String name);
}