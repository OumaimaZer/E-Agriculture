package estm.dut.eagri.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estm.dut.eagri.dao.Esp32Reposistory;
import estm.dut.eagri.model.Esp32;

@Service
public class Esp32Impl implements Esp32Service{
    @Autowired
    private Esp32Reposistory espRepository;

   
    @Override
    public Esp32 save(Esp32 esp) {
        return (Esp32) espRepository.save(esp);
    }

    @Override
    public Esp32 findByMacaddress(String macaddress) {
        Esp32 esp= espRepository.findByMacaddress(macaddress).get(0);
        return esp;
    }

    public List<Esp32> findListByMacaddress(String macaddress) {
        List<Esp32> esp =espRepository.findByMacaddress(macaddress);
        return esp;
    }

    @Override
    public List<Esp32> findAll() {
        List<Esp32> esp = new ArrayList<>();
        esp = espRepository.findAll();
        return esp;

    }
}