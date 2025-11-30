package estm.dut.eagri.services;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import estm.dut.eagri.model.Plant.PlantApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlantApiService {
    
    public List<PlantApi> getPlantsFromAPI() throws IOException {
        String apiUrl="https://trefle.io/api/v1/plants?token=6ceI1nHzNNYimUto7_cyujvFRIJUnycJUu9wY4KUZik";
        List<PlantApi> plantsapi = new ArrayList<>();

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
        JSONArray plantsArray = json.getJSONArray("data");

        for (int i = 0; i < plantsArray.length(); i++) {
            JSONObject plantJson = plantsArray.getJSONObject(i);
            String commonName = plantJson.getString("common_name");
            String scientificName = plantJson.getString("scientific_name");
            String family = plantJson.getString("family");
            String familyCommonName;
            if (plantJson.isNull("family_common_name")) {
                familyCommonName = "null";
            } else {
                familyCommonName = plantJson.getString("family_common_name");
            }

            String imageUrl = plantJson.getString("image_url");

            plantsapi.add(new PlantApi(commonName, scientificName, family, familyCommonName, imageUrl));
        }

        return plantsapi;
    }

    public PlantApi getPlantsByName(String commonNameFilter) throws IOException {
        String apiUrl = "https://trefle.io/api/v1/plants?token=6ceI1nHzNNYimUto7_cyujvFRIJUnycJUu9wY4KUZik&filter[common_name]=" + commonNameFilter.replace(" ", "%20");
        PlantApi plants = new PlantApi();
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
        JSONArray plantsArray = json.getJSONArray("data");

        for (int i = 0; i < plantsArray.length(); i++) {
            JSONObject plantJson = plantsArray.getJSONObject(i);
            String commonName = plantJson.getString("common_name");
            String scientificName = plantJson.getString("scientific_name");
            String family = plantJson.getString("family");
            String familyCommonName;
            if (plantJson.isNull("family_common_name")) {
                familyCommonName = "null";
            } else {
                familyCommonName = plantJson.getString("family_common_name");
            }

            String imageUrl = plantJson.getString("image_url");

            plants= new PlantApi(commonName, scientificName, family, familyCommonName, imageUrl);
        }

        return plants;
    }
}