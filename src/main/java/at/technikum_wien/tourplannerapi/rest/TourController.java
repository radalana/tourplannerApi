package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.dto.TourDTO;
import at.technikum_wien.tourplannerapi.dto.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.mapper.TourMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.service.TourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/tours")
@Slf4j
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourMapper tourMapper;

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
    public Tour createTour(@Valid @RequestBody TourDTO data) {
        Tour createdTour = service.saveTour(tourMapper.map(data));
        log.info("Returning response: {}", createdTour);
        return createdTour;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourDTO updateTour(@PathVariable Long id, @Valid @RequestBody TourUpdateDTO tourData) {
        TourDTO updatedTour = service.updateTour(id, tourData);
        log.info("Update tour: {}", updatedTour);
        return updatedTour;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable Long id) {
        service.deleteTour(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<TourDTO> searchTours(@RequestParam String query) {
        return service.searchToursByText(query);
    }

    @PostMapping("/import")
    public ResponseEntity<TourDTO> importTour(@Valid @RequestBody TourDTO tourDTO) {
        Tour savedTour = tourService.saveTour(tourMapper.map(tourDTO));
        return ResponseEntity.ok(tourMapper.map(savedTour));
    }

}
