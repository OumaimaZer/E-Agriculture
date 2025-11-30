package estm.dut.eagri.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.UserServiceImpl;


@Controller
@RequestMapping("/eagri/clients/auth")
public class LoginController{


@Autowired
    private UserServiceImpl userServiceImpl;

    
@GetMapping("/login")
public String getForm(Model model){
    User user = new User();
    model.addAttribute("user", user);
    return "login"; 
}

@PostMapping(value="/login")
public  String submitForm(Model model,@ModelAttribute("user") User user) {
    User test = userServiceImpl.findByEmail(user.getEmail());
   
    
    if (user.getEmail()==null || user.getEmail().isBlank()){
        model.addAttribute("message", "Fill the email field.");
        return "login";
    }
    if (user.getPassword()==null || user.getPassword().isBlank()){
        model.addAttribute("message", "Fill the password field.");
        return "login";
    }
    if(test == null) {
        model.addAttribute("message", "User does not exist.");
        return "login";
    }

    
    if(test.getPassword().equals(user.getPassword())) {
        return "redirect:/";
    } else {
        model.addAttribute("message", "Incorrect password.");
        return "login";
    }
}
}
