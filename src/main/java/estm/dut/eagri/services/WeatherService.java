// package estm.dut.eagri.services;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import org.json.JSONObject;
// import org.springframework.stereotype.Service;
// import estm.dut.eagri.model.Weather;
 
// @Service
// public class WeatherService {
    
//     private final static String api_key="THE API KEY";

//     public Weather data(double latitude, double longitude) throws IOException {
//         String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + api_key;
//         URL obj = new URL(url);
//         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//         con.setRequestMethod("GET");

//         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//         String inputLine;
//         StringBuilder response = new StringBuilder();

//         while ((inputLine = in.readLine()) != null) {
//             response.append(inputLine);
//         }
//         in.close();

//         JSONObject jsonObject = new JSONObject(response.toString());
//         double temp = (jsonObject.getJSONObject("main").getDouble("temp"))-273.15;
//         double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
//         double pressure=jsonObject.getJSONObject("main").getDouble("pressure");
//         JSONObject rainobj= jsonObject.optJSONObject("rain");
//        double rain = -1; 
//        if (rainobj != null) { 
//       rain = rainobj.optDouble("1h", -1); 
//       }
//         System.out.println("temperature :    " +"   " + temp + "   " +", pressure :    " + pressure +"   " +"humidity :   " +humidity +"   " +"rain  :   " +rain);
//         return new Weather(temp,humidity,pressure,rain);
//     }
// }
