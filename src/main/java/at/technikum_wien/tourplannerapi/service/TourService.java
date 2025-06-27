package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.TourDTO;
import at.technikum_wien.tourplannerapi.dto.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.exception.ResourceNotFoundExeption;
import at.technikum_wien.tourplannerapi.exception.RouteNotFoundException;
import at.technikum_wien.tourplannerapi.mapper.TourMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private RouteService routeService;

    public Iterable<Tour> getAllTours() {
        Iterable<Tour> tours = repository.findAll();
        return tours;
    }

    public Tour getTourById(Long id) {
        Tour tour = repository.findById(id).orElse(null);
        return tour;
    }

    public TourDTO updateTour(Long id, TourUpdateDTO tourData) throws ResourceNotFoundExeption, RouteNotFoundException {
        var tour = repository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Tour with " + id + " not found"));
        JSONObject orsResponse = routeService.fetchRoute(
                tourData.getFromLocation(),
                tourData.getToLocation(),
                tourData.getTransportType()
        );

        JSONObject summary = orsResponse.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .getJSONArray("segments")
                .getJSONObject(0);
        double rawKm = summary.getDouble("distance") / 1000.0;
        double distanceInKm = new BigDecimal(rawKm)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        long durationInSec = (long) summary.getDouble("duration");
        tourMapper.update(tourData, tour);
        tour.setDistance(distanceInKm);
        tour.setEstimatedTime(durationInSec);
        repository.save(tour);
        return tourMapper.map(tour);
    }

    public Tour saveTour(Tour tour) {
        log.info("Saving tour: {}", tour);
        //get duration and distance from ors
        JSONObject orsResponse = routeService.fetchRoute(tour.getFromLocation(), tour.getToLocation(), tour.getTransportType());
        JSONObject summary = orsResponse.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .getJSONArray("segments")
                .getJSONObject(0);
        double rawKm = summary.getDouble("distance") / 1000.0;
        double distanceInKm = new BigDecimal(rawKm)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        long durationInSec = (long) summary.getDouble("duration");
        tour.setDistance(distanceInKm);
        tour.setEstimatedTime(durationInSec);
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
