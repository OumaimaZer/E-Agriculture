package estm.dut.eagri.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Year;
import java.util.List;
import org.springframework.ui.Model;

import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.Esp32;
import estm.dut.eagri.model.Weather;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.OpenMeteoService;

@Controller
@RequestMapping("/eagri/clients")
public class HomeController {

  @GetMapping("/submit")
  public String getForm(Model model, Authentication authentication) {
    UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();
    model.addAttribute("user", user);
    model.addAttribute("userEsp", user.getListeEsp());

    return "redirect:/eagri/clients/";
  }

  @GetMapping("/user")
    @ResponseBody
    public User deleteList(Model model, Authentication authentication) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("currentUser", user);
        return user;
    }

  @PostMapping("/submit")
  public String submit(Authentication authentication, @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
      Model model) { 
    List<Weather> forecast;
    List<Weather> current;

    UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
    User user = userDetails.getUser();
    model.addAttribute("userEsp", user.getListeEsp());

    try {

      forecast = OpenMeteoService.getForecast(latitude, longitude, "8");
      model.addAttribute("forecast", forecast);

      for (int i = 1; i < forecast.size(); i++) {
        String day = "day" + i;
        model.addAttribute(day, forecast.get(i));
      }

      current = OpenMeteoService.getWeatherData(latitude, longitude);
      model.addAttribute("current", current); 

      String startdate = (Year.now().getValue() - 2)+"-01-01";
      String enddate = (Year.now().getValue() - 1)+"-12-31";
      List<Weather> history = OpenMeteoService.getHistWeather(latitude, longitude, startdate, enddate);
      model.addAttribute("history", history);


    } catch (Exception e) {
      e.printStackTrace();
    }

    return "home";
  }

}