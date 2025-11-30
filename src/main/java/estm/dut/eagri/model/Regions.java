package estm.dut.eagri.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Regions")
public class Regions {
    @MongoId
    private String ID ;

    private String name;
    private String description;
    private double minLatitude;
    private double maxLatitude;
    private double minLongitude;
    private double maxLongitude;

    public Regions(){}
    
    public Regions(String name, String description, double minLatitude, double maxLatitude, double minLongitude,
            double maxLongitude) {
        this.name = name;
        this.description = description;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
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
    public double getMinLatitude() {
        return minLatitude;
    }
    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }
    public double getMaxLatitude() {
        return maxLatitude;
    }
    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }
    public double getMinLongitude() {
        return minLongitude;
    }
    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }
    public double getMaxLongitude() {
        return maxLongitude;
    }
    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }
}