package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.TourLogDTO;
import at.technikum_wien.tourplannerapi.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplannerapi.exception.ResourceNotFoundExeption;
import at.technikum_wien.tourplannerapi.mapper.TourLogMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourLogService {
    @Autowired
    private TourLogMapper tourLogMapper;
    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourRepository tourRepository;

    public List<TourLogDTO> fetchLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId).stream()
                .map(tourLogMapper::map)
                .collect(Collectors.toList());
    }

    public TourLogDTO saveLog(TourLogDTO data, Long tourId) {
        TourLog tourLog = tourLogMapper.map(data);
        return tourRepository.findById(tourId).map(tour -> {
            tourLog.setTour(tour);
            TourLog createdTourLog = tourLogRepository.save(tourLog);
            return tourLogMapper.map(createdTourLog);
        }).orElseThrow(() -> new ResourceNotFoundExeption("Tour with id: " + tourId + " is not found"));
    }

    public TourLogDTO updateTourLog(Long tourId, Long id, TourLogUpdateDTO data) throws BadRequestException {
        TourLog log = tourLogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Tour log with id: " + id + " is not found"));
        if (!log.getTour().getId().equals(tourId)) {
            throw new BadRequestException("Log with id: " + id + " does not belong to tour with id" + tourId);
        }
        tourLogMapper.update(data, log);
        tourLogRepository.save(log);
        return tourLogMapper.map(log);
    }

    public void deleteLog(Long id, Long tourId) throws BadRequestException {
        TourLog log = tourLogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Tour log with id: " + id + " is not found"));
        if (!log.getTour().getId().equals(tourId)) {
            throw new BadRequestException("Log with id: " + id + " does not belong to tour with id" + tourId);
        }
        tourLogRepository.deleteById(id);
    }

    public List<TourLogDTO> searchLogsByText(Long tourId, String text) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new ResourceNotFoundExeption("Tour with id: " + tourId + " is not found"));
        List<TourLog> logs = tourLogRepository.searchLogsByComment(tourId, text);
        return logs.stream().map(tourLogMapper::map).collect(Collectors.toList());
    }
}
