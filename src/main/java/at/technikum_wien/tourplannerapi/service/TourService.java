package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.TourDTO;
import at.technikum_wien.tourplannerapi.dto.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.exception.ResourceNotFoundExeption;
import at.technikum_wien.tourplannerapi.mapper.TourMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourService {
    @Autowired
    private TourRepository repository;

    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private TourMapper tourMapper;

    public Iterable<Tour> getAllTours() {
        Iterable<Tour> tours = repository.findAll();
        return tours;
    }

    public Tour getTourById(Long id) {
        Tour tour = repository.findById(id).orElse(null);
        return tour;
    }

    public TourDTO updateTour(Long id, TourUpdateDTO tourData) throws ResourceNotFoundExeption {
        var tour = repository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Tour with " + id + " not found"));
        tourMapper.update(tourData, tour);
        repository.save(tour);
        return tourMapper.map(tour);
    }

    public Tour saveTour(Tour tour) {
        log.info("Saving tour: {}", tour);
        return repository.save(tour);
    }



    public void deleteTour(Long id) {
        repository.deleteById(id);
    }

    public List<TourDTO> searchToursByText(String searchText) {
    List<Tour> matchingTours = repository.searchTours(searchText);
    return matchingTours.stream().map(tourMapper::map).collect(Collectors.toList());
}

}
