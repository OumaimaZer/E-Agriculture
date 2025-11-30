package estm.dut.eagri.services;
// package pfe.jee.eagriculture.login.services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Import;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Service
// // @Import( MyWebConfig.class )
// public class SecurityServImpl  implements SecurityServ{
    
//     @Autowired
//     private UserDetailsService userDetailsService;

//     // @Qualifier("myAuthenticationManager")
//     @Autowired
//     private AuthenticationManager authenticationManager;

//     private static final Logger logger = LoggerFactory.getLogger(SecurityServImpl.class);
    
//     @Override
//     public void autoLogin(String email, String password) {
//         UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

//         authenticationManager.authenticate(usernamePasswordAuthenticationToken);

//         if (usernamePasswordAuthenticationToken.isAuthenticated()) {
//             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//             logger.debug(String.format("login %s successfully!", email));
//         }

//         }

    
//     @Override
//     public boolean isAuthenticated() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
//             return false;
//         }
//         return authentication.isAuthenticated();
        
//     }
// }
