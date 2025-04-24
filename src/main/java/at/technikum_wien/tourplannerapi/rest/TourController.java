package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.service.TourService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/api")
@RestController
public class TourController {
    private final TourService service;
    public TourController(TourService service) {
        this.service = service;
    }

    @GetMapping("/tours")
    public Iterable<Tour> getTours() {
        return service.getAllTours();
    }
}
