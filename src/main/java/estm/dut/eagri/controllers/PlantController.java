package estm.dut.eagri.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import estm.dut.eagri.config.UserInfoUserDetails;
import estm.dut.eagri.model.Regions;
import estm.dut.eagri.model.Weather;
import estm.dut.eagri.model.Plant.Plants;
import estm.dut.eagri.model.UserInfo.User;
import estm.dut.eagri.services.OpenMeteoService;
import estm.dut.eagri.services.PlantServiceImpl;
import estm.dut.eagri.services.SectorsService;
import estm.dut.eagri.services.RegionsService;


@Controller
@RequestMapping("/eagri/clients")
public class PlantController {

    @Autowired
    private PlantServiceImpl plantServiceImpl;

    @Autowired
    private RegionsService regionsService;

    @Autowired
    private SectorsService sectorsService;

    @GetMapping("/plantSuggestion")
    public String getForm(Authentication authentication, Model model) {
        LocalDate currentDate = LocalDate.now();
        String season = getSeasonForMonth(currentDate.getMonthValue());

        List<Plants> allPlants = plantServiceImpl.findBySeason(season);

        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Double latitude = user.getAgricultureZone().getLatitude();
        Double longitude = user.getAgricultureZone().getLongitude();

        List<Double> historyTemperature = getHistory(latitude, longitude, "temperature", season);
        List<Double> historyHumidity = getHistory(latitude, longitude, "humidity", season);
        List<Double> historyRain = getHistory(latitude, longitude, "rain", season);

        // List<Double> forecastTemperature = getForecast(latitude, longitude, "temperature");
        // List<Double> forecastHumidity = getForecast(latitude, longitude, "humidity");
        // List<Double> forecastRain = getForecast(latitude, longitude, "rain");

        double averageTemperature = calculateAverage(historyTemperature);
        double averageHumidity = calculateAverage(historyHumidity);
        double averageRain = (calculateAverage(historyRain)*1000);

        model.addAttribute("humidity", averageHumidity);
        model.addAttribute("rain", averageRain);
        model.addAttribute("temperature", averageTemperature);
        model.addAttribute("plants", allPlants);

        if (user.getAgricultureZone().getCountry().equals("MA")) {
            String region = user.getAgricultureZone().getRegion();
            Regions regionDetails = regionsService.findByName(region);
            String description = region + ":\n" + regionDetails.getDescription();
            model.addAttribute("regionDescription", description);
            HashMap<String, Long> sectors = sectorsService.findByRegionName(region);
            model.addAttribute("recommendedSectors", sectors);
        } else {
            model.addAttribute("regionDescription", "The agriculture zone is outside of the Moroccan borders");
            model.addAttribute("recommendedSectors", null);
        }

        return "plants";
    }

    @PostMapping("/plantSuggestion")
    public String getRecommendedPlants(
        
            @ModelAttribute("season") String season,
            @ModelAttribute("sector") String sector,
            Authentication authentication,
            Model model
    ) {
        List<Plants> allPlants = new ArrayList<>();
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        System.out.println("season : "+season+" sector "+sector);
        if (sector.equals("All")) {
            allPlants = plantServiceImpl.findBySeason(season);
            model.addAttribute("plants", allPlants);
                        System.out.println(allPlants.get(0).toString());

        } else {
            allPlants = plantServiceImpl.findBySeasonAndSector(season, sector);
            model.addAttribute("plants", allPlants);
            System.out.println(allPlants.get(0).toString());
        }
        if (user.getAgricultureZone().getCountry().equals("MA")) {
            String region = user.getAgricultureZone().getRegion();
            Regions regionDetails = regionsService.findByName(region);
            String description = region + ":\n" + regionDetails.getDescription();
            model.addAttribute("regionDescription", description);
            HashMap<String, Long> sectors = sectorsService.findByRegionName(region);
            model.addAttribute("recommendedSectors", sectors);
        } else {
            model.addAttribute("regionDescription", "The agriculture zone is outside of the Moroccan borders");
            model.addAttribute("recommendedSectors", null);
        }
        
        Double latitude = user.getAgricultureZone().getLatitude();
        Double longitude = user.getAgricultureZone().getLongitude();

        List<Double> historyTemperature = getHistory(latitude, longitude, "temperature", season);
        List<Double> historyHumidity = getHistory(latitude, longitude, "humidity", season);
        List<Double> historyRain = getHistory(latitude, longitude, "rain", season);

        // List<Double> forecastTemperature = getForecast(latitude, longitude, "temperature");
        // List<Double> forecastHumidity = getForecast(latitude, longitude, "humidity");
        // List<Double> forecastRain = getForecast(latitude, longitude, "rain");

        double averageTemperature = calculateAverage(historyTemperature);
        double averageHumidity = calculateAverage(historyHumidity);
        double averageRain = (calculateAverage(historyRain)*1000);
        System.out.println("average rain : "+averageRain);
        System.out.println("old average rain : "+averageRain/1000);

        model.addAttribute("humidity", averageHumidity);
        model.addAttribute("rain", averageRain);
        model.addAttribute("temperature", averageTemperature);

        return "plants";
    }

   private List<Double> getHistory(Double latitude, Double longitude, String choice, String season) {
        List<Double> data = new ArrayList<>();
        int startYear = 1970;
        int endYear = (Year.now().getValue() - 1);
        String startDate ="";
        String endDate ="";
        
        for (int i = startYear; i <= endYear; i++) {
            if(choice != "rain"){
               startDate = LocalDate.of(i, getStartMonthForSeason(season), 1) + "";
               endDate = LocalDate.of(i, getEndMonthForSeason(season), 1).withDayOfMonth(1) + "";  
            }else{
               startDate = LocalDate.of(i, 1, 1) + "";
               endDate = LocalDate.of(i, 12, 31).withDayOfMonth(1) + "";
            }
            
            List<Weather> weathers = OpenMeteoService.getHistWeather(latitude, longitude, startDate, endDate);
        
            for (Weather weather : weathers) {
                if (choice.equals("temperature")) {
                    data.add(weather.getTemp());
                } else if (choice.equals("humidity")) {
                    data.add(weather.getHumidity());
                } else if (choice.equals("rain")) {
                    data.add(weather.getPrecipitation());
                }
            }
        }

        return data;
    }

    // private List<Double> getForecast(Double latitude, Double longitude, String choice) {
    //     List<Weather> weathers = new ArrayList<>();
    //     try {
    //         weathers = OpenMeteoService.getForecast(latitude, longitude, "16");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    //     List<Double> data = new ArrayList<>();
    //     for (Weather weather : weathers) {
    //         if (choice.equals("temperature")) {
    //             data.add(weather.getTemp());
    //         } else if (choice.equals("humidity")) {
    //             data.add(weather.getHumidity());
    //         } else if (choice.equals("rain")) {
    //             data.add(weather.getPrecipitation());
    //         }
    //     }

    //     return data;
    // }

    private double calculateAverage(List<Double> data1) {
        double sum1 = 0;        

        int size1 = data1.size();

        for (Double d : data1) {
            sum1 += d;
        }

        sum1 = sum1 / size1;

        
        return sum1;
    }

    private String getSeasonForMonth(int month) {
        // Determine the season based on the month
        if (month >= 3 && month <= 5) {
            return "Spring";
        } else if (month >= 6 && month <= 8) {
            return "Summer";
        } else if (month >= 9 && month <= 11) {
            return "Autumn";
        } else {
            return "Winter";
        }
    }

    private int getStartMonthForSeason(String season) {
        switch (season) {
            case "Spring":
                return 3;
            case "Summer":
                return 6;
            case "Autumn":
                return 9;
            case "Winter":
                return 12;
            default:
                return 1;
        }
    }

    private int getEndMonthForSeason(String season) {
        switch (season) {
            case "Spring":
                return 5;
            case "Summer":
                return 8;
            case "Autumn":
                return 11;
            case "Winter":
                return 2;
            default:
                return 1;
        }
    }
}