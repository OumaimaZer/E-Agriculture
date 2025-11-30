package estm.dut.eagri.model.UserInfo;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import estm.dut.eagri.model.Esp32;


@Document(collection = "User")
public class User {

    @MongoId
    private String ID;

    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public User() {
    }

    @Indexed(unique = true)
    private String phone ; 
    @Indexed(unique = true)
    private String email; 
    private String f_name;
    private String l_name;
    private String roles;
    private List<Esp32> listeEsp;
    private String password;
    private AgricultureZone agricultureZone;

    public User(String phone, String email, String f_name, String l_name, String roles, List<Esp32> listeEsp,
            String password, AgricultureZone agricultureZone) {
        this.phone = phone;
        this.email = email;
        this.f_name = f_name;
        this.l_name = l_name;
        this.roles = roles;
        this.listeEsp = listeEsp;
        this.password = password;
        this.agricultureZone = agricultureZone;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getF_name() {
        return f_name;
    }
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
    public String getL_name() {
        return l_name;
    }
    public void setL_name(String l_name) {
        this.l_name = l_name;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public List<Esp32> getListeEsp() {
        return listeEsp;
    }
    public void setListeEsp(List<Esp32> listeEsp) {
        this.listeEsp = listeEsp;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public AgricultureZone getAgricultureZone() {
        return agricultureZone;
    }
    public void setAgricultureZone(AgricultureZone agricultureZone) {
        this.agricultureZone = agricultureZone;
    }
    @Override
    public String toString() {
        return "User [ID=" + ID + ", phone=" + phone + ", email=" + email + ", f_name=" + f_name + ", l_name=" + l_name
                + ", roles=" + roles + ", listeEsp=" + listeEsp + ", password=" + password + ", agricultureZone="
                + agricultureZone + "]";
    }
}