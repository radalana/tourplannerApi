package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.TourLogDTO;
import at.technikum_wien.tourplannerapi.exception.ResourceNotFoundExeption;
import at.technikum_wien.tourplannerapi.mapper.TourLogMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourLogService {
    @Autowired
    private TourLogMapper tourLogMapper;
    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourRepository tourRepository;

    public List<TourLog> getLogsForTour(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public Optional<TourLog> getLogById(Long id) {
        return tourLogRepository.findById(id);
    }

    public TourLogDTO saveLog(TourLogDTO data, Long tourId) {
        TourLog tourLog = tourLogMapper.map(data);
        return tourRepository.findById(tourId).map(tour -> {
            tourLog.setTour(tour);
            TourLog createdTourLog = tourLogRepository.save(tourLog);
            return tourLogMapper.map(createdTourLog);
        }).orElseThrow(() -> new ResourceNotFoundExeption("Tour with id: " + tourId + " is not found"));
    }

    public void deleteLog(Long id) {
        tourLogRepository.deleteById(id);
    }
    //for popularity
    public int getLogCountForTour(Tour tour) {
        return tourLogRepository.countTourLogsByTour(tour);
    }
    //for child-friendliness
    public double calculateChildFriendliness(Tour tour) {
        List<TourLog> logs = tourLogRepository.getAllLogsByTour(tour);
        if (logs.isEmpty()) return 0.0;

        double avgDifficulty = logs.stream().mapToDouble(TourLog::getDifficulty).average().orElse(0);
        double avgTime = logs.stream().mapToDouble(TourLog::getTotalDuration).average().orElse(0);
        double avgDistance = logs.stream().mapToDouble(TourLog::getTotalDistance).average().orElse(0);

        double score = 10.0 - ((avgDifficulty * 2) + (avgTime / 10.0) + (avgDistance / 10.0)) / 3.0;
        return Math.max(0.0, Math.min(10.0, score)); //between 0 and 10
    }
    //full text search
    public List<TourLog> searchLogsByComment(String searchText) {
        return tourLogRepository.searchLogsByComment(searchText);
    }
}
