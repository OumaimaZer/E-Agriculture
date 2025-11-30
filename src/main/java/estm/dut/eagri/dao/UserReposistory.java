package estm.dut.eagri.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import estm.dut.eagri.model.UserInfo.User;

public interface UserReposistory extends MongoRepository<User, String> {

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByID(String id);

    void deleteByID(String id);

    void deleteByEmail(String email);

}
