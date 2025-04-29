package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TourService {
    @Autowired
    private TourRepository repository;

    public Iterable<Tour> getAllTours() {
        return repository.findAll();
    }

    public Tour getTourById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Tour saveTour(Tour tour) {
        log.info("Saving tour: {}", tour);
        return repository.save(tour);
    }

    public void deleteTour(Long id) {
        repository.deleteById(id);
    }
}
