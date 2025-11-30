package estm.dut.eagri.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import estm.dut.eagri.model.UserInfo.AgricultureZone;

@Document(collection = "Esp32")
public class Esp32 implements Serializable{
    public Esp32(){}

    private String macaddress;
    private String time;
    private int rain;
    private Double temperature;
    private Double humidity;
    private AgricultureZone agricultureZone;

    public String getMacaddress() {
        return macaddress;
    }
    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getRain() {
        return rain;
    }
    public void setRain(int rain) {
        this.rain = rain;
    }
    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public Double getHumidity() {
        return humidity;
    }
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
    public AgricultureZone getAgricultureZone() {
        return agricultureZone;
    }
    public void setAgricultureZone(AgricultureZone agricultureZone) {
        this.agricultureZone = agricultureZone;
    }
    
    public Esp32(String macaddress, String time, int rain, Double temperature, Double humidity,
            AgricultureZone agricultureZone) {
        this.macaddress = macaddress;
        this.time = time;
        this.rain = rain;
        this.temperature = temperature;
        this.humidity = humidity;
        this.agricultureZone = agricultureZone;
    }
    @Override
    public String toString() {
        return "Esp32 [macaddress=" + macaddress + ", time=" + time + ", rain=" + rain + ", temperature=" + temperature
                + ", humidity=" + humidity + ", agricultureZone=" + agricultureZone + "]";
    }
  
}