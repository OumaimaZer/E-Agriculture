package estm.dut.eagri.services;

import java.util.List;

import estm.dut.eagri.model.Plant.Plants;

public interface PlantService {
    List<Plants> findAll();

    List<Plants> findBySeason(String season);

    List<Plants> findBySector(String sector);

    List<Plants> findBySeasonAndSector(String season, String sector);

    Plants findByName(String name);
}