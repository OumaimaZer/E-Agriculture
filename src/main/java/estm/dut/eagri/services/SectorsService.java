package estm.dut.eagri.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estm.dut.eagri.dao.SectorsRepository;
import estm.dut.eagri.model.Plant.Sectors;

@Service
public class SectorsService {
    @Autowired
    private SectorsRepository SectorsRepository;

    public List<Sectors> findAll() {
        List<Sectors> sectors = new ArrayList<>();
        sectors = SectorsRepository.findAll();
        return sectors;
    }

    public HashMap<String, Long> findByRegionName(String hashMapValue) {
        HashMap<String, Long> suggestedSectors = new HashMap<>();
        List<Sectors> sectors = SectorsRepository.findAll();
        if (sectors != null) {
            for (Sectors sector : sectors) {
                HashMap<String, Long> regions = sector.getRegionMap();
                if (regions != null) {
                    for (Map.Entry<String, Long> entry : regions.entrySet()) {
                        if (entry.getKey().contains(hashMapValue)) {
                            Long percentage = entry.getValue();
                            suggestedSectors.put(sector.getName(), percentage);
                            break; // Optional: If you want to stop searching after finding the first match
                        }
                    }
                }
            }
        }
        return suggestedSectors;
    }
}