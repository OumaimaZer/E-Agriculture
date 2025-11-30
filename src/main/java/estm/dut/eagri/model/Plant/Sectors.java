package estm.dut.eagri.model.Plant;

import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Sectors")
public class Sectors {
    @MongoId
    private String ID;


    private String name;
    private String description;
    @Field("regions")
    private HashMap<String,Long> RegionMap;


    public Sectors(){}
    public Sectors(String iD, String name, String description, HashMap<String, Long> regionMap) {
        ID = iD;
        this.name = name;
        this.description = description;
        RegionMap = regionMap;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Long> getRegionMap() {
        return RegionMap;
    }

    public void setRegionMap(HashMap<String, Long> regionMap) {
        RegionMap = regionMap;
    }
    
   
}