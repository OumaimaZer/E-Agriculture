package estm.dut.eagri.model.Plant;

public class Season {
    
    private boolean fullYear;
    private String name;
    
    public Season(boolean fullYear, String name) {
        this.fullYear = fullYear;
        this.name = name;
    }
    public boolean isFullYear() {
        return fullYear;
    }
    public void setFullYear(boolean fullYear) {
        this.fullYear = fullYear;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
