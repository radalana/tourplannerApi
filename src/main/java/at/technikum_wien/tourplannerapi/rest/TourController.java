package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.dto.TourDTO;
import at.technikum_wien.tourplannerapi.dto.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
@Slf4j
public class TourController {
    private final TourService service;
    public TourController(TourService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Tour> getTours() {
        return service.getAllTours();
    }

    @GetMapping("/{id}")
    public Tour getTour(@PathVariable Long id) {
        return service.getTourById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tour createTour(@RequestBody Tour tour) {
        Tour createdTour = service.saveTour(tour);
        log.info("Returning response: {}", createdTour);
        return createdTour;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tour updateTour(@PathVariable Long id, @RequestBody TourUpdateDTO tourData) {
        return service.updateTour(id, tourData);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        service.deleteTour(id);
    }
}
