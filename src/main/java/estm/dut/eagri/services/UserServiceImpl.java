package estm.dut.eagri.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import estm.dut.eagri.dao.UserReposistory;
import estm.dut.eagri.model.UserInfo.User;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
        
        @Autowired
        private UserReposistory mongoReposistory;
        
        
        public void CreateUser(User user) {
            user.setRoles("USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            mongoReposistory.save(user);
        }
    
        public User findByEmail(String email) {
            User ou = mongoReposistory.findByEmail(email);

            return ou;
        }
    
        @Override
        public User findByPhone(String phone) {
            User us=mongoReposistory.findByPhone(phone);
            return us;
        } 

        @Override
        public User FindById(String id) {
           User userid = mongoReposistory.findByID(id);
           return userid;
        }  

        @Override
        public User updateUser(User newUser, User oldUser,Boolean pass) {
            mongoReposistory.deleteByEmail(oldUser.getEmail());
            newUser.setRoles("USER");
            if(pass){
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));}
          return mongoReposistory.save(newUser);
        }
    
        @Override
        public void deleteUser(String id) {
            mongoReposistory.deleteById(id);
        }

    
    }