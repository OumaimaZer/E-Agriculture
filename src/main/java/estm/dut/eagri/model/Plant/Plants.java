package estm.dut.eagri.model.Plant;

import java.io.Serializable; 

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Plant")
public class Plants implements Serializable{
    @MongoId
    private String ID;

    private String name;
    private String sector;
    private String description;
    private double max_humidity;
    private double min_humidity;
    private double max_temperature;
    private double min_temperature;
    private double max_watering;
    private double min_watering;
    private String season;
    private long growthPeriodInDays;

    public Plants() {
    }   
    
    public Plants(String name, String sector, String description, double max_humidity, double min_humidity,
            double max_temperature, double min_temperature, double max_watering, double min_watering, String season,
            long growthPeriodInDays) {
        this.name = name;
        this.sector = sector;
        this.description = description;
        this.max_humidity = max_humidity;
        this.min_humidity = min_humidity;
        this.max_temperature = max_temperature;
        this.min_temperature = min_temperature;
        this.max_watering = max_watering;
        this.min_watering = min_watering;
        this.season = season;
        this.growthPeriodInDays = growthPeriodInDays;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getMax_humidity() {
        return max_humidity;
    }
    public void setMax_humidity(double max_humidity) {
        this.max_humidity = max_humidity;
    }
    public double getMin_humidity() {
        return min_humidity;
    }
    public void setMin_humidity(double min_humidity) {
        this.min_humidity = min_humidity;
    }
    public double getMax_temperature() {
        return max_temperature;
    }
    public void setMax_temperature(double max_temperature) {
        this.max_temperature = max_temperature;
    }
    public double getMin_temperature() {
        return min_temperature;
    }
    public void setMin_temperature(double min_temperature) {
        this.min_temperature = min_temperature;
    }
    public double getMax_watering() {
        return max_watering;
    }
    public void setMax_watering(double max_watering) {
        this.max_watering = max_watering;
    }
    public double getMin_watering() {
        return min_watering;
    }
    public void setMin_watering(double min_watering) {
        this.min_watering = min_watering;
    }
    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }
    public long getGrowthPeriodInDays() {
        return growthPeriodInDays;
    }
    public void setGrowthPeriodInDays(long growthPeriodInDays) {
        this.growthPeriodInDays = growthPeriodInDays;
    }
    
}