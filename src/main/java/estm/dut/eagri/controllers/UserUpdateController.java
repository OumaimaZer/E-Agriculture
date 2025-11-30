package estm.dut.eagri.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.model.Regions;
import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.UserInfo.AgricultureZone;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.RegionsService;
import estm.dut.eagri.services.UserServiceImpl;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/eagri/clients/user")
public class UserUpdateController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RegionsService regionService;

    private User currentUser;

    @GetMapping("/updateUser")
    public String getUpdateUser(Model model , Authentication authentication){
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        currentUser = userDetails.getUser();
        model.addAttribute("user", currentUser);
        return "updateUser.html";
    }

    @GetMapping("/deleteUser")
    public String getDeleteUser(Model model,Authentication authentication){
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
       
        try {
            userServiceImpl.deleteUser(user.getID());
            String message= "User "+user.getF_name()+" "+user.getL_name()+" has been deleted successfully";
            model.addAttribute("message", message);
            SecurityContextHolder.clearContext(); 
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         return "redirect:/eagri/clients/auth/signup";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @ModelAttribute("agricultureZone") AgricultureZone agricultureZone, Model model) {
 

    if (user.getF_name()==null || user.getF_name().isBlank()){
        model.addAttribute("message", "Fill the first name field.");
        return "updateUser.html";
    }
    if (user.getL_name()==null || user.getL_name().isBlank()){
        model.addAttribute("message", "Fill the last name field.");
        return "updateUser.html";
    }
    if (user.getEmail()==null || user.getEmail().isBlank()){
        model.addAttribute("message", "Fill the email field.");
        return "updateUser.html";
    }
    if (user.getPhone()==null || user.getPhone().isBlank()){
        model.addAttribute("message", "Fill the phone field.");
        return "updateUser.html";
    }
 
    String regex = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";  
        Pattern pattern = Pattern.compile(regex);   
        Matcher matcher = pattern.matcher(user.getEmail());  
        if(!matcher.matches()){  
            String message="Please use an email like:\n example@gmail.exp";
            model.addAttribute("message", message);
            return "updateUser.html";

        }

        Pattern pattern1=Pattern.compile("^(05|06|07|08)[0-9]{8}");
         Matcher matcher1=pattern1.matcher(user.getPhone());
         if (!matcher1.matches()){
            model.addAttribute("message", "Please use a valid phone number");
            return "updateUser.html";

         }
          
            try {
                user.setPassword(currentUser.getPassword());
                if(agricultureZone.getCountry().equals("MA")){
                    List<Regions> regions = regionService.findAll();
                 for (Regions region : regions) {
                     if (agricultureZone.getLatitude() >= region.getMinLatitude() && agricultureZone.getLatitude() <= region.getMaxLatitude()
                             && agricultureZone.getLongitude() >= region.getMinLongitude() && agricultureZone.getLongitude() <= region.getMaxLongitude()) {
                         agricultureZone.setRegion(region.getName());
                     }
                 } 
                 }
                user.setAgricultureZone(agricultureZone); 
               User test = userServiceImpl.updateUser(user,currentUser,false);
               SecurityContextHolder.clearContext();
             
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return "updateUser.html";
        
}

@GetMapping("/resetPassword")
public String resetPassword(Model model,Authentication authentication){
    UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
    currentUser = userDetails.getUser();
    model.addAttribute("user", currentUser);
    return "resetPassword.html";
}

@PostMapping("/resetPassword")
public String resetPassword(Model model,@ModelAttribute("password") String pass,Authentication authentication, HttpSession session) {
     // Check password format
     Pattern pattern2 = Pattern.compile("^(?=.\\d)(?=.[a-zA-Z]).{6,20}$");
     Matcher matcher2 = pattern2.matcher(pass);
     if (!matcher2.matches()) {
        model.addAttribute("message","Please use a password between 6 and 20 characters with at least one letter and one number and a symbol.");
     }

    if(bCryptPasswordEncoder.matches(pass, currentUser.getPassword())){
        model.addAttribute("message", "Your password is the same as the old one.");
    }
    else{
        try {
            User nv=currentUser;
            nv.setPassword(pass);
            userServiceImpl.updateUser(nv,currentUser,true);
            model.addAttribute("message", "Password reset successfully");
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Password reset failed");
        }
    }
    return "resetPassword.html";
}
}