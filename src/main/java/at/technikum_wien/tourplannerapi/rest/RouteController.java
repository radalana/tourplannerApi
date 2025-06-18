package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.service.RouteService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/route")
    public ResponseEntity<String> getRoute(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        try {
            String geoJson = routeService.getRouteGeoJson(from, to);
            return ResponseEntity.ok(geoJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Failed to fetch route\"}");
        }
    }
}
