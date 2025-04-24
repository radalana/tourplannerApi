package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    @Autowired
    private TourRepository repository;

    public Iterable<Tour> getAllTours() {
        return this.repository.findAll();
    }
}
