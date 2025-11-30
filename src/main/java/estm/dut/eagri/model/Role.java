package estm.dut.eagri.model;

import org.springframework.data.annotation.Id;

public class Role {
    @Id

    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}