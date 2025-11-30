package estm.dut.eagri.controllers;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/eagri/clients/monitoring")
public class Esp32Controller {
    @Autowired
    private Esp32Impl esp32Service;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RegionsService regionService;

    @PostMapping("/save/user_esp")
    public String addESP32(@ModelAttribute("mac") String mac, @ModelAttribute("agriZone") AgricultureZone agriZone,
            Authentication authentication, Model model) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        User newUser = userDetails.getUser();
        Esp32 esp = new Esp32();
        esp.setAgricultureZone(agriZone);
        esp.setMacaddress(mac);
        List<Esp32> listeEsp = new ArrayList<>();
        if (user.getListeEsp() != null) {
            listeEsp = user.getListeEsp();
        }

        listeEsp.add(esp);
        newUser.setListeEsp(listeEsp);
        try {
            userServiceImpl.updateUser(newUser, user, false);
            model.addAttribute("message", "Esp added successfully , please re-enter the page");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Could not add the Esp.");
        }

        return "esp32.html";
    }

    @GetMapping("/")
    public String save(Authentication authentication, Model model) {
        try {
            // Getting the authenticated user data
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            model.addAttribute("testList", user.getListeEsp());
            if (user.getListeEsp() != null) {
                List<Esp32> espList = new ArrayList<>();
                List<String> macs = new ArrayList<>();

                for (Esp32 esp : user.getListeEsp()) {
                    String macAddress = esp.getMacaddress();

                    // Create an instance of MqttSubscriber with the MAC address
                    MqttSubscriber mqttSubscriber = new MqttSubscriber(macAddress);
                    Esp32 receivedEsp32 = mqttSubscriber.receiveMessage();
                    AgricultureZone agriZone = esp.getAgricultureZone();

                    if (receivedEsp32 != null && receivedEsp32.getMacaddress() != null) {
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
                        receivedEsp32.setAgricultureZone(agriZone);
                        espList.add(receivedEsp32);
                        esp32Service.save(receivedEsp32);
                    }
                    macs.add(macAddress);
                } 
                // current weather data
                model.addAttribute("newEsp", espList);
                // existing mac adress
                model.addAttribute("macs", macs); 
                // Getting the default history of esp data
                try {
                    String defaultEsp = user.getListeEsp().get(0).getMacaddress();
                    // getHistory(model, defaultEsp);
                    if(defaultEsp != null){
                      List<Esp32> history = esp32Service.findListByMacaddress(defaultEsp);
                    model.addAttribute("espHistory", history);  
                    }
                    else {
                        model.addAttribute("error", "No past history for you monitors !");
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                model.addAttribute("newEsp", null);
                model.addAttribute("error", "No monitor found !");
                model.addAttribute("espHistory", null);
                model.addAttribute("selectedMac", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "esp32";
    }

    // Wrapper class for esp32 variable
    class Esp32Wrapper {
        Esp32 esp32;
    }

    @PostMapping(value = "/history")
    public String getHistory(Model model, String mac, Authentication authentication) throws MqttException, InterruptedException {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            if (user.getListeEsp() != null) {
                List<Esp32> espList = new ArrayList<>();
                List<String> macs = new ArrayList<>();

                for (Esp32 esp : user.getListeEsp()) {
                    String macAddress = esp.getMacaddress();

                    // Create an instance of MqttSubscriber with the MAC address
                    MqttSubscriber mqttSubscriber = new MqttSubscriber(macAddress);
                    Esp32 receivedEsp32 = mqttSubscriber.receiveMessage();
                    AgricultureZone agriZone = esp.getAgricultureZone();

                    if (receivedEsp32 != null && receivedEsp32.getMacaddress() != null) {
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
                        receivedEsp32.setAgricultureZone(agriZone);
                        espList.add(receivedEsp32);
                        esp32Service.save(receivedEsp32);
                    }
                    macs.add(macAddress);
                }

                // current weather data
                model.addAttribute("newEsp", espList);
                // existing mac adress
                model.addAttribute("macs", macs);

                // Getting the default history of esp data
                try {
                    String defaultEsp = mac;
                    // getHistory(model, defaultEsp);
                    if(defaultEsp != null){
                      List<Esp32> history = esp32Service.findListByMacaddress(defaultEsp);
                    model.addAttribute("espHistory", history);  
                    }
                    else {
                        model.addAttribute("error", "No past history for you monitors !");
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else { 
                model.addAttribute("newEsp", null);
                model.addAttribute("error", "No monitor found !");
                model.addAttribute("espHistory", null);
                model.addAttribute("selectedMac", null);
            }

        return "esp32";
    }

}