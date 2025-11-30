package estm.dut.eagri.services;

import estm.dut.eagri.model.UserInfo.User;

public interface UserService {

    User findByEmail(String email);

    User findByPhone(String phone);

    void CreateUser(User user);

    User updateUser(User newUser, User oldUser, Boolean pass);

    void deleteUser(String id);

    User FindById(String id);

}
