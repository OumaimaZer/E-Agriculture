package estm.dut.eagri.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estm.dut.eagri.dao.RegionsRepository;
import estm.dut.eagri.model.Regions;

@Service
public class RegionsService {
    @Autowired
    private RegionsRepository regionsReposistory;
    
    public List<Regions> findAll() {
        List<Regions> regions = new ArrayList<>();
        regions = regionsReposistory.findAll();
        return regions;
    }

    public Regions findByName(String name){
        return regionsReposistory.findByName(name);
    }
}