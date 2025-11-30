package estm.dut.eagri.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.UserInfo.User; 

@Controller
@RequestMapping("/eagri/clients")
public class WelcomeController {
  @Autowired
  private HomeController homeController;
 

  @GetMapping("/")
  public String welcome(Model model, Authentication authentication) {
    UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();
    Double latitude = user.getAgricultureZone().getLatitude();
    Double longitude = user.getAgricultureZone().getLongitude();
    homeController.submit(authentication, latitude, longitude, model);
    return "home";
  }

}