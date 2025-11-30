package estm.dut.eagri.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.Esp32;
import estm.dut.eagri.model.Regions;
import estm.dut.eagri.model.UserInfo.AgricultureZone;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.Esp32Impl;
import estm.dut.eagri.services.RegionsService;
import estm.dut.eagri.services.UserServiceImpl;

@Controller
@RequestMapping("/eagri/clients/monitor")
public class EspUpdateController {
    @Autowired
    private Esp32Impl esp32Service;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RegionsService regionService;

    @PostMapping("/update")
    public String updateESP32(@RequestParam("mac") String mac, @RequestParam("oldMac") String oldMac,
            @ModelAttribute("agriZone") AgricultureZone agriZone,
            Authentication authentication, Model model) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        User newUser = userDetails.getUser();
        List<Esp32> listeEsp = user.getListeEsp();
        if (listeEsp != null) {
            for (Esp32 userEsp : listeEsp) {
                if (userEsp.getMacaddress().equals(oldMac)) {
                    if (agriZone.getCountry().equals("MA")) {
                            List<Regions> regions = regionService.findAll();
                            for (Regions region : regions) {
                                if (agriZone.getLatitude() >= region.getMinLatitude()
                                        && agriZone.getLatitude() <= region.getMaxLatitude()
                                        && agriZone.getLongitude() >= region.getMinLongitude()
                                        && agriZone.getLongitude() <= region.getMaxLongitude()) {
                                    agriZone.setRegion(region.getName());
                                    break;
                                }
                            }
                        }
                    userEsp.setAgricultureZone(agriZone);
                    userEsp.setMacaddress(mac);
                    break;
                }
            }
            newUser.setListeEsp(listeEsp);
            try {
                userServiceImpl.updateUser(newUser, user, false);
                model.addAttribute("message", "Monitor updated successfully");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "Could not update the Monitor.");
            }
        }
        return "espUpdate.html";
    }

    @GetMapping("/")
    public String getForm(Model model, Authentication authentication) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("listeEsp", user.getListeEsp());

        return "espUpdate.html";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Esp32> deleteList(Model model, Authentication authentication) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("listeEsp", user.getListeEsp());
        return user.getListeEsp();
    }

    @GetMapping("/esp/{mac}")
    @ResponseBody
    public Esp32 esp(@PathVariable("mac") String mac, Model model, Authentication authentication) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        for (Esp32 esp32 : user.getListeEsp()) {
            if(esp32.getMacaddress().equals(mac)){
                return esp32;
            }
        }
        model.addAttribute("esp", user.getListeEsp());
        return null;
    }

    @PostMapping("/delete/{mac}")
    @ResponseBody
    public String deleteESP32(@PathVariable("mac") String mac, Authentication authentication, Model model) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        User newUser = userDetails.getUser();
        List<Esp32> listeEsp = user.getListeEsp();
        String message = "";
        if (listeEsp != null) {
            for (Esp32 userEsp : listeEsp) {
                if (userEsp.getMacaddress().equals(mac)) {
                    listeEsp.remove(userEsp);
                    break;
                }
            }
            newUser.setListeEsp(listeEsp);
            try {
                message = "Monitor deleted successfully";
                userServiceImpl.updateUser(newUser, user, false);
            } catch (Exception e) {
                message = "Could not delete the Monitor.";
                e.printStackTrace();
            }
        }
        return message;
    }
}