package estm.dut.eagri.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.model.Regions;
import estm.dut.eagri.model.UserInfo.AgricultureZone;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.RegionsService;
import estm.dut.eagri.services.UserServiceImpl;


@Controller
@RequestMapping("/eagri/clients/auth")
public class SignupController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RegionsService regionService;

    @GetMapping("/signup")
    public String getSignup(Model model){
        
        User user = new User();
        model.addAttribute("user", user);
        return "signup"; 
}


@PostMapping(value="/signup")
    public String submitSignup(@ModelAttribute("user") User user,@ModelAttribute("agricultureZone") AgricultureZone agricultureZone, Model model) {
    
    User us1=userServiceImpl.findByEmail(user.getEmail());
    User us2=userServiceImpl.findByPhone(user.getPhone()); 

    if (user.getF_name()==null || user.getF_name().isBlank()){
        model.addAttribute("message", "Fill the first name field.");
        return "signup";
    }
    if (user.getL_name()==null || user.getL_name().isBlank()){
        model.addAttribute("message", "Fill the last name field.");
        return "signup";
    }
    if (user.getEmail()==null || user.getEmail().isBlank()){
        model.addAttribute("message", "Fill the email field.");
        return "signup";
    }
    if (user.getPhone()==null || user.getPhone().isBlank()){
        model.addAttribute("message", "Fill the phone field.");
        return "signup";
    }
    if (user.getPassword()==null || user.getPassword().isBlank()){
        model.addAttribute("message", "Fill the password field.");
        return "signup";
    } 
    String regex = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";  
        Pattern pattern = Pattern.compile(regex);   
        Matcher matcher = pattern.matcher(user.getEmail());  
        if(!matcher.matches()){  
            String message="Please use an email like:\n example@gmail.exp";
            model.addAttribute("message", message);
        return "signup";

        }

        Pattern pattern1=Pattern.compile("^(05|06|07|08)[0-9]{8}");
         Matcher matcher1=pattern1.matcher(user.getPhone());
         if (!matcher1.matches()){
            model.addAttribute("message", "Please use a valid phone number");
        return "signup";

         }
 
        if(us1!=null){
            model.addAttribute("message", "Someone is using this email.");
        return "signup";

        }
        if(us2!=null){
            model.addAttribute("message", "Someone is using this phone number.");
        return "signup";

        }
        else{
            try {
                user.setAgricultureZone(agricultureZone);
                if(agricultureZone.getCountry().equals("MA")){
                   List<Regions> regions = regionService.findAll();
                for (Regions region : regions) {
                    if (agricultureZone.getLatitude() >= region.getMinLatitude() && agricultureZone.getLatitude() <= region.getMaxLatitude()
                            && agricultureZone.getLongitude() >= region.getMinLongitude() && agricultureZone.getLongitude() <= region.getMaxLongitude()) {
                        agricultureZone.setRegion(region.getName());
                    }
                } 
                }
                
                userServiceImpl.CreateUser(user);   
              } catch (Exception e) { 
                  e.printStackTrace(); 
              }
              return "redirect:/eagri/clients/auth/login";

        }
}
}