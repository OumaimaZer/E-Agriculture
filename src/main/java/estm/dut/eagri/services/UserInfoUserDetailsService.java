package estm.dut.eagri.services;



import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.UserInfo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userInfo = repository.findByEmail(email);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        UserInfoUserDetails test=new UserInfoUserDetails(userInfo);
        return test;
    }
}