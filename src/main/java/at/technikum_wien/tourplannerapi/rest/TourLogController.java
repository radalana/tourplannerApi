package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.service.TourLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourlogs")
public class TourLogController {

    private final TourLogService tourLogService;

    public TourLogController(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
    }

    @GetMapping("/tour/{tourId}")
    public List<TourLog> getLogsForTour(@PathVariable Long tourId) {
        return tourLogService.getLogsForTour(tourId);
    }

    @GetMapping("/{id}")
    public TourLog getTourLog(@PathVariable Long id) {
        return tourLogService.getLogById(id).orElse(null);
    }

    @PostMapping
    public TourLog createTourLog(@RequestBody TourLog tourLog) {
        return tourLogService.saveLog(tourLog);
    }

    @PutMapping("/{id}")
    public TourLog updateTourLog(@PathVariable Long id, @RequestBody TourLog tourLog) {
        tourLog.setId(id);
        return tourLogService.saveLog(tourLog);
    }

    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable Long id) {
        tourLogService.deleteLog(id);
    }
}
