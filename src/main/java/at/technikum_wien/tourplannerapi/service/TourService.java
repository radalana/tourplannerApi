package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TourService {
    @Autowired
    private TourRepository repository;
    @Autowired
    private TourLogService tourLogService;

    public Iterable<Tour> getAllTours() {
        Iterable<Tour> tours = repository.findAll();
        //calculated childfriendly and popularity
        tours.forEach(this::enrichWithComputedAttributes);
        return tours;
    }

    public Tour getTourById(Long id) {
        Tour tour = repository.findById(id).orElse(null);
        if (tour != null) {
            enrichWithComputedAttributes(tour);
        }
        return tour;
    }

    public Tour saveTour(Tour tour) {
        log.info("Saving tour: {}", tour);
        return repository.save(tour);
    }

    public void deleteTour(Long id) {
        repository.deleteById(id);
    }

    public List<Tour> searchToursByText(String searchText) {
    List<Tour> matchingTours = repository.searchTours(searchText);
    matchingTours.forEach(this::enrichWithComputedAttributes);
    return matchingTours;
}
    private void enrichWithComputedAttributes(Tour tour) {
        int popularity = tourLogService.getLogCountForTour(tour);
        double childFriendliness = tourLogService.calculateChildFriendliness(tour);

        tour.setPopularity(popularity);
        tour.setChildFriendliness(childFriendliness);
    }
}
