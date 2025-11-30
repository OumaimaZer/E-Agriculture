package estm.dut.eagri.services;

public interface SecurityServ {
    boolean isAuthenticated();

    void autoLogin(String username, String password);
}
