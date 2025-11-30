package estm.dut.eagri.services;

import java.util.List;
import estm.dut.eagri.model.Esp32;

public interface Esp32Service {
    Esp32 save(Esp32 esp);

    Esp32 findByMacaddress(String macaddress);

    List<Esp32> findAll();

    List<Esp32> findListByMacaddress(String mac);
}