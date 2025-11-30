package estm.dut.eagri.model.Plant;

public class PlantApi {

    private  String commonName;
    private  String scientificName; 
    private  String family;
    private  String familyCommonName;
    private  String imageUrl;

    public PlantApi() {
    }
 
    public PlantApi(String commonName, String scientificName, String family, String familyCommonName, String imageUrl) {
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.family = family;
        this.familyCommonName = familyCommonName;
        this.imageUrl = imageUrl;
    }
    
    public String getCommonName() {
        return commonName;
    }
    public String getScientificName() {
        return scientificName;
    }
    public String getFamily() {
        return family;
    }
    public String getFamilyCommonName() {
        return familyCommonName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}