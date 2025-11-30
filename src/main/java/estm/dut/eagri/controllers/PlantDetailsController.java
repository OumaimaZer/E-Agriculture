package estm.dut.eagri.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.Plant.PlantApi;
import estm.dut.eagri.model.Plant.Plants;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.PlantApiService;
import estm.dut.eagri.services.PlantServiceImpl;

@RequestMapping("/eagri/clients")
@Controller
public class PlantDetailsController {

  @Autowired
  private PlantServiceImpl plantService;

  @Autowired
  private PlantApiService plantApiService;

  @GetMapping("/plant/{name}")
  public String getForm(Model model, Authentication authentication, @PathVariable("name") String plant)
      throws IOException {
    UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();
    model.addAttribute("user", user);

    Plants plantFound = plantService.findByName(plant);
    model.addAttribute("plant", plantFound);

    PlantApi plantApiFound = plantApiService.getPlantsByName(plant);
    model.addAttribute("plantApi", plantApiFound);

    return "plant";
  }
}