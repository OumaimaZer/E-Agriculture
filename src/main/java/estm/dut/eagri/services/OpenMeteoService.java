package estm.dut.eagri.services;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.io.*;
import org.json.*;
import org.springframework.stereotype.Service;

import estm.dut.eagri.model.Weather;
import java.util.ArrayList;
import java.util.*;

@Service
public class OpenMeteoService {

    public static List<Weather> getForecast(double latitude, double longitude, String forecast_days)
            throws IOException {
        String parameter = "temperature_2m_max,temperature_2m_min,precipitation_sum,windspeed_10m_max,windgusts_10m_max,rain_sum";
        String timeZone = getTimeZoneFromLatLong(latitude, longitude);

        String baseUrl = "https://api.open-meteo.com/v1/forecast";
        String url = baseUrl + "?latitude=" + latitude + "&longitude=" + longitude + "&forecast_days=" + forecast_days
                + "&daily=" + parameter + "&timezone=" + timeZone;
        URL openMeteoAPI = new URL(url);
        URLConnection conn = openMeteoAPI.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject dailyObject = jsonObject.getJSONObject("daily");

        List<Weather> dailyWeatherList = new ArrayList<Weather>();
        JSONArray timeArray = dailyObject.getJSONArray("time");
        JSONArray tempmaxarray = dailyObject.getJSONArray("temperature_2m_max");
        JSONArray tempminarray = dailyObject.getJSONArray("temperature_2m_min");
        JSONArray precipitationArray = dailyObject.getJSONArray("precipitation_sum");//
        JSONArray windspeedArray = dailyObject.getJSONArray("windspeed_10m_max");
        JSONArray windguestsArray = dailyObject.getJSONArray("windgusts_10m_max");
        JSONArray rainArray = dailyObject.getJSONArray("rain_sum");

        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < timeArray.length(); i++) {
            LocalDate dates = currentDate.plusDays(i);
            String date = dates.getDayOfWeek().toString();
            double tempmax = tempmaxarray.getDouble(i);
            double tempmin = tempminarray.getDouble(i);
            double precipitation = precipitationArray.getDouble(i);
            double windspeed = windspeedArray.getDouble(i);
            double windguests = windguestsArray.getDouble(i);
            double rain = rainArray.getDouble(i);

            Weather weather = new Weather(tempmax, tempmin, precipitation, windspeed, windguests, rain, date);
            dailyWeatherList.add(weather);
        }
        return dailyWeatherList;
    }

    public static List<Weather> getWeatherData(Double latitude, Double longitude) throws Exception {
        String hourlyParameters = "temperature_2m,relativehumidity_2m,pressure_msl,precipitation,rain";
        String baseUrl = "https://api.open-meteo.com/v1/forecast";
        String apiUrl = baseUrl + "?latitude=" + latitude + "&longitude=" + longitude + "&forecast_days=1" + "&hourly="
                + hourlyParameters;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject hourlyObject = jsonObject.getJSONObject("hourly");

        List<Weather> hourlyWeatherList = new ArrayList<Weather>();
        JSONArray timeArray = hourlyObject.getJSONArray("time");
        JSONArray tempArray = hourlyObject.getJSONArray("temperature_2m");
        JSONArray rhArray = hourlyObject.getJSONArray("relativehumidity_2m");
        JSONArray pressureArray = hourlyObject.getJSONArray("pressure_msl");
        JSONArray precipitationArray = hourlyObject.getJSONArray("precipitation");
        JSONArray rainArray = hourlyObject.getJSONArray("rain");
        for (int i = 0; i < timeArray.length(); i++) {
            double temp = tempArray.getDouble(i);
            double humidity = rhArray.getDouble(i);
            double pressure = pressureArray.getDouble(i);
            double precipitation = precipitationArray.getDouble(i);
            double rain = rainArray.getDouble(i);

            Weather weather = new Weather(temp, humidity, pressure, precipitation, rain);
            hourlyWeatherList.add(weather);
        }

        return hourlyWeatherList;
    }

    public static List<Weather> getLast10DaysWeather(double latitude, double longitude) {
        String parameter = "temperature_2m,relativehumidity_2m,pressure_msl,precipitation,rain";
        String pastDays = "10";
        List<Weather> weatherList = new ArrayList<>();
        try {
            String baseUrl = "https://api.open-meteo.com/v1/forecast";
            String url = baseUrl + "?latitude=" + latitude + "&longitude=" + longitude + "&past_days=" + pastDays
                    + "&hourly=" + parameter;

            URL openMeteoAPI = new URL(url);
            URLConnection conn = openMeteoAPI.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray timeArray = json.getJSONObject("hourly").getJSONArray("time");
            JSONArray tempArray = json.getJSONObject("hourly").getJSONArray("temperature_2m");
            JSONArray rhArray = json.getJSONObject("hourly").getJSONArray("relativehumidity_2m");
            JSONArray pressureArray = json.getJSONObject("hourly").getJSONArray("pressure_msl");
            JSONArray precipitationArray = json.getJSONObject("hourly").getJSONArray("precipitation");
            JSONArray rainArray = json.getJSONObject("hourly").getJSONArray("rain");

            for (int i = timeArray.length() - 1; i >= timeArray.length() - 10; i--) {
                double temp = tempArray.getDouble(i);
                double humidity = rhArray.getDouble(i);
                double pressure = pressureArray.getDouble(i);
                double precipitation = precipitationArray.getDouble(i);
                double rain = rainArray.getDouble(i);
                Weather weather = new Weather(temp, humidity, pressure, precipitation, rain);
                weatherList.add(weather);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherList;
    } 

    public static List<Weather> getHistWeather(double latitude, double longitude, String startDate, String endDate) {
        String hourlyParams = "temperature_2m,relativehumidity_2m,pressure_msl,precipitation,rain";
        List<Weather> weatherList = new ArrayList<>();
    
        try {
            String baseUrl = "https://archive-api.open-meteo.com/v1/era5";
            String apiUrl = baseUrl + "?latitude=" + URLEncoder.encode(String.valueOf(latitude), StandardCharsets.UTF_8)
                    + "&longitude=" + URLEncoder.encode(String.valueOf(longitude), StandardCharsets.UTF_8)
                    + "&start_date=" + URLEncoder.encode(startDate, StandardCharsets.UTF_8)
                    + "&end_date=" + URLEncoder.encode(endDate, StandardCharsets.UTF_8)
                    + "&hourly=" + URLEncoder.encode(hourlyParams, StandardCharsets.UTF_8);
    
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
    
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
    
            JSONObject json = new JSONObject(response.toString());
            JSONArray timeArray = json.getJSONObject("hourly").getJSONArray("time");
            JSONArray tempArray = json.getJSONObject("hourly").getJSONArray("temperature_2m");
            JSONArray humidityArray = json.getJSONObject("hourly").getJSONArray("relativehumidity_2m");
            JSONArray pressureArray = json.getJSONObject("hourly").getJSONArray("pressure_msl");
            JSONArray precipitationArray = json.getJSONObject("hourly").getJSONArray("precipitation");
            JSONArray rainArray = json.getJSONObject("hourly").getJSONArray("rain");
    
            for (int i = 0; i < timeArray.length(); i++) {
                double temp = tempArray.isNull(i) ? 0.0 : tempArray.getDouble(i);
                double humidity = humidityArray.isNull(i) ? 0.0 : humidityArray.getDouble(i);
                double pressure = pressureArray.isNull(i) ? 0.0 : pressureArray.getDouble(i);
                double precipitation = precipitationArray.isNull(i) ? 0.0 : precipitationArray.getDouble(i);
                double rain = rainArray.isNull(i) ? 0.0 : rainArray.getDouble(i); 
                String date = timeArray.isNull(i) ? null : timeArray.getString(i);
    
                Weather weather = new Weather(temp, humidity, pressure, precipitation, rain, date);
                weatherList.add(weather);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return weatherList;
    }
    

    public static String getTimeZoneFromLatLong(double lat, double lon) {
        String timeZoneId = "";
        double timezoneOffset = 0;
        String[] timezones = TimeZone.getAvailableIDs();
        for (String timezone : timezones) {
            TimeZone tz = TimeZone.getTimeZone(timezone);
            double offset = tz.getOffset(System.currentTimeMillis()) / 3600000.0;
            if (offset == timezoneOffset) {
                return tz.getID();
            } else if (timezoneOffset == 0 || Math.abs(offset) < Math.abs(timezoneOffset)) {
                timezoneOffset = offset;
                timeZoneId = tz.getID();
            }
        }
        return timeZoneId;
    }
}