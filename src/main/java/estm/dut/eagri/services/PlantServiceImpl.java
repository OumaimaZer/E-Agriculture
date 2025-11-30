package estm.dut.eagri.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estm.dut.eagri.dao.PlantRepository;
import estm.dut.eagri.model.Plant.Plants;

@Service
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository plantReposistory;


    @Override
    public List<Plants> findAll() {
        List<Plants> plants = new ArrayList<>();
        plants = plantReposistory.findAll();
        return plants;
    }

    @Override
    public List<Plants> findBySector(String sector) {
        List<Plants> plant = new ArrayList<>();
        plant = plantReposistory.findBySector(sector);
        return plant;
    }

    @Override
    public List<Plants> findBySeasonAndSector(String season, String sector) {
        List<Plants> plant =new ArrayList<>();
        plant =plantReposistory.findBySeasonAndSector(season,sector);
        return plant;
    }

    @Override
    public List<Plants> findBySeason(String season) {
        List<Plants> plant = new ArrayList<>();
        plant = plantReposistory.findBySeason(season);
        return plant;
    }

    @Override
    public Plants findByName(String name) {
        Plants plant= new Plants();
        plant= plantReposistory.findByName(name);
        return plant;
    }

   

}