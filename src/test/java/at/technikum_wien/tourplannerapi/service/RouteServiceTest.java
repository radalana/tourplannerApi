package at.technikum_wien.tourplannerapi.service;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RouteServiceTest {

    private RouteService routeService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        routeService = new RouteService();

        //inject dummy API key
        Field apiKeyField = RouteService.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(routeService, "dummy-key");

        //inject mocked RestTemplate
        Field restTemplateField = RouteService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplate = mock(RestTemplate.class);
        restTemplateField.set(routeService, restTemplate);
    }

    // Test 1: Fetch route with valid parameters
    @Test
    void testFetchRoute_success_returnsJson() {
        // Mock geocoding JSON response
        String geoJson = """
        {
          "features": [{
            "geometry": {
              "coordinates": [16.3738, 48.2082]
            }
          }]
        }
        """;

        // Mock route JSON response
        String routeJson = """
        {
          "routes": [{
            "summary": {
              "distance": 1000,
              "duration": 300
            }
          }]
        }
        """;

        // First two calls: geocoding for 'from' and 'to'
        when(restTemplate.getForObject(contains("geocode"), eq(String.class)))
                .thenReturn(geoJson)
                .thenReturn(geoJson);

        // Third call: directions
        when(restTemplate.getForObject(contains("directions"), eq(String.class)))
                .thenReturn(routeJson);

        JSONObject result = routeService.fetchRoute("Vienna", "Graz", "driving-car");

        assertNotNull(result);
        assertTrue(result.has("routes"));
        verify(restTemplate, times(2)).getForObject(contains("geocode"), eq(String.class));
        verify(restTemplate).getForObject(contains("directions"), eq(String.class));
    }

    // Test 2: Fetch route with invalid parameters
    @Test
    void testFetchRoute_geocodeFails_throwsException() {
        String emptyGeocode = """
        {
          "features": []
        }
        """;

        // Simulate geocode failure
        when(restTemplate.getForObject(contains("geocode"), eq(String.class)))
                .thenReturn(emptyGeocode);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                routeService.fetchRoute("Nowhere", "Graz", "driving-car"));

        assertEquals("Failed to geocode one or both locations.", ex.getMessage());
    }
}
