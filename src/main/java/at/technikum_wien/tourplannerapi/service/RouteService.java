package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.exception.RouteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class RouteService {

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";
    private static final String DIRECTIONS_URL = "https://api.openrouteservice.org/v2/directions/";
    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject fetchRoute(String from, String to, String transport) {
        log.info("GEO from = " + from);
        try {
        double[] fromCoords = geocodeLocation(from);
        double[] toCoords = geocodeLocation(to);
        if (fromCoords == null || toCoords == null) {
            throw new RuntimeException("Failed to geocode one or both locations.");
        }
        String url = DIRECTIONS_URL + transport;

            String routeUrl = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", apiKey)
                    .queryParam("start", fromCoords[1] + "," + fromCoords[0])
                    .queryParam("end", toCoords[1] + "," + toCoords[0])
                    .toUriString();

            log.info("Requesting route from ORS: " + routeUrl);

            String response = restTemplate.getForObject(routeUrl, String.class);
            return new JSONObject(response);

        } catch (HttpClientErrorException e) {
            log.warn("ORS returned error: {}", e.getResponseBodyAsString());
            throw new RouteNotFoundException("No accessible route found between the entered points.");
        } catch (Exception e) {
            log.error("Unexpected error during route request: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected route error", e);
        }
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
        if (features.length() == 0) return null;

        JSONArray coordinates = features.getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        // return [lat, lng]
        return new double[]{coordinates.getDouble(1), coordinates.getDouble(0)};
    }
}
