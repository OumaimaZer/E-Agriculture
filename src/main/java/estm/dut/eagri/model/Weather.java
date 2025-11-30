package estm.dut.eagri.model;

public class Weather {
    
    private double temp ; 
    private double humidity ; 
    private double pressure ; 
    private double precipitation ; 
    private double windspeed ;
    private double windgusts ;
    
    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWindgusts() {
        return windgusts;
    }

    public void setWindgusts(double windgusts) {
        this.windgusts = windgusts;
    }
    private double rain ;
    private String date ;
    private double temp_max;
    private double temp_min;
    private double soil ; 

    public Weather(double temp, double humidity, double precipitation, String date, double soil) {
        this.temp = temp;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.date = date;
        this.soil = soil;
    }

    public double getSoil() {
        return soil;
    }

    public void setSoil(double soil) {
        this.soil = soil;
    }

    public Weather(double temp, double humidity, double pressure, double precipitation, double rain,String date) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.precipitation = precipitation;
        this.rain = rain;
        this.date = date;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
    
   

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getPrecipitation() {
        return precipitation;
    }
    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }
    public double getRain() {
        return rain;
    }
    public void setRain(double rain) {
        this.rain = rain;
    }
    public Weather(double temp, double humidity, double pressure, double precipitation, double rain) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.precipitation = precipitation;
        this.rain = rain;
    }
    public Weather(double temp_max, double temp_min, double precipitation, double windspeed,double windgusts, double rain, String date ) {
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.precipitation = precipitation;
        this.windspeed = windspeed;
        this.windgusts = windgusts;
        this.rain = rain;
        this.date=date ; 
    }

    @Override
    public String toString() {
        return "Weather [temp=" + temp + ", humidity=" + humidity + ", pressure=" + pressure + ", precipitation="
                + precipitation + ", windspeed=" + windspeed + ", windgusts=" + windgusts + ", rain=" + rain + ", date="
                + date + ", temp_max=" + temp_max + ", temp_min=" + temp_min + ", soil=" + soil + "]";
    }
}