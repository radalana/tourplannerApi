package at.technikum_wien.tourplannerapi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;


@Service
public class RouteService {

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";
    private static final String DIRECTIONS_URL = "https://api.openrouteservice.org/v2/directions/driving-car";

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject fetchRoute(String from, String to) {
        double[] fromCoords = geocodeLocation(from);
        double[] toCoords = geocodeLocation(to);

        if (fromCoords == null || toCoords == null) {
            throw new RuntimeException("Failed to geocode one or both locations.");
        }

        String routeUrl = UriComponentsBuilder.fromHttpUrl(DIRECTIONS_URL)
                .queryParam("api_key", apiKey)
                .queryParam("start", fromCoords[1] + "," + fromCoords[0])
                .queryParam("end", toCoords[1] + "," + toCoords[0])
                .toUriString();

        String response = restTemplate.getForObject(routeUrl, String.class);
        return new JSONObject(response);
    }

    public String getRouteGeoJson(String from, String to) {
        double[] fromCoords = geocodeLocation(from);
        double[] toCoords = geocodeLocation(to);

        if (fromCoords == null || toCoords == null) {
            throw new RuntimeException("Failed to geocode one or both locations.");
        }

        String url = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

        JSONObject requestBody = new JSONObject()
                .put("coordinates", new JSONArray()
                        .put(new JSONArray().put(fromCoords[1]).put(fromCoords[0])) // [lng, lat]
                        .put(new JSONArray().put(toCoords[1]).put(toCoords[0])));

        return restTemplate.postForObject(
                url + "?api_key=" + apiKey,
                requestBody.toString(),
                String.class
        );
    }

    private double[] geocodeLocation(String locationName) {
        String geocodeUrl = UriComponentsBuilder.fromHttpUrl(GEOCODE_URL)
                .queryParam("api_key", apiKey)
                .queryParam("text", locationName)
                .queryParam("size", 1)
                .toUriString();

        String response = restTemplate.getForObject(geocodeUrl, String.class);
        JSONObject json = new JSONObject(response);

        JSONArray features = json.getJSONArray("features");
        if (features.isEmpty()) return null;

        JSONArray coordinates = features.getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        // return [lat, lng]
        return new double[]{coordinates.getDouble(1), coordinates.getDouble(0)};
    }
}
