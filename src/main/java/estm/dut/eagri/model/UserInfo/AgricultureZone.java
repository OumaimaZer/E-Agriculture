package estm.dut.eagri.model.UserInfo;

public class AgricultureZone {
    private double latitude ; 
    private double longitude ;
    private String city ; 
    private String country ; 
    private String region ;

    
    public AgricultureZone() {
    }
    
    public AgricultureZone(double latitude, double longitude, String city, String country, String region) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.region = region;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }   
}