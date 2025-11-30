package estm.dut.eagri.dao;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estm.dut.eagri.model.Esp32;

@Repository
@Document(collection = "Esp")
public interface Esp32Reposistory extends MongoRepository<Esp32, String> {
    Esp32 save(Esp32 esp);

    List<Esp32> findByMacaddress(String macaddress);

    List<Esp32> findAll();
}