package estm.dut.eagri.model.Plant;

public class PlantRecom {
    private Plants plant;
    private Boolean recommanded;
    
    public PlantRecom() {
    }
    public PlantRecom(Plants plant, Boolean recommanded) {
        this.plant = plant;
        this.recommanded = recommanded;
    }
    public Plants getPlant() {
        return plant;
    }
    public void setPlant(Plants plant) {
        this.plant = plant;
    }
    public Boolean getRecommanded() {
        return recommanded;
    }
    public void setRecommanded(Boolean recommanded) {
        this.recommanded = recommanded;
    }
    @Override
    public String toString() {
        return "PlantRecom [plant=" + plant + ", recommanded=" + recommanded + "]";
    }
}