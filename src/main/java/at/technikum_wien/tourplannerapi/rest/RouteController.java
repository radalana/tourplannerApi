package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.service.RouteService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/route")
    public ResponseEntity<String> getRoute(@RequestParam String from, @RequestParam String to) {
        try {
            JSONObject route = routeService.fetchRoute(from, to);
            return ResponseEntity.ok(route.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
