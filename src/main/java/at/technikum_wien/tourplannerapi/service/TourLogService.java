package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourLogService {
    @Autowired
    private TourLogRepository tourLogRepository;

    public List<TourLog> getLogsForTour(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public Optional<TourLog> getLogById(Long id) {
        return tourLogRepository.findById(id);
    }

    public TourLog saveLog(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }

    public void deleteLog(Long id) {
        tourLogRepository.deleteById(id);
    }
}
