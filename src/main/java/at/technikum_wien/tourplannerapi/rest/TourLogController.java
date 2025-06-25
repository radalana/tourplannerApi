package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.dto.TourLogDTO;
import at.technikum_wien.tourplannerapi.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplannerapi.service.TourLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@Slf4j
public class TourLogController {


    private final TourLogService tourLogService;

    public TourLogController(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
    }

    @GetMapping("/{tourId}/logs")
    @ResponseStatus(HttpStatus.OK)
    public List<TourLogDTO> fetchLogsByTourId(@PathVariable Long tourId) {
        log.debug("Received GET for tour with id {} ", tourId);
        return tourLogService.fetchLogsByTourId(tourId);
    }


    @PostMapping("/{tourId}/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public TourLogDTO createTourLog(@RequestBody TourLogDTO data, @PathVariable Long tourId, HttpServletRequest request) {
        log.debug("Received POST {} with body: {}", request.getRequestURI(), data);
        return tourLogService.saveLog(data, tourId);
    }

    @PutMapping("/{tourId}/logs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TourLogDTO updateTourLog(@PathVariable Long tourId, @PathVariable Long id, @RequestBody TourLogUpdateDTO data) throws BadRequestException {
            TourLogDTO updatedLog = tourLogService.updateTourLog(tourId, id, data);
            log.info("Updated tour log: {}", updatedLog);
            return updatedLog;
    }

    @DeleteMapping("/{tourId}/logs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTourLog(@PathVariable Long tourId, @PathVariable Long id) throws BadRequestException {
        log.debug("Received DELETE for tour with id {} ", id);
        tourLogService.deleteLog(id, tourId);
    }

    @GetMapping("{tourId}/logs/search")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<TourLogDTO> searchLogs(@PathVariable Long tourId, @RequestParam String query) {
        log.debug("Received GET for logs search with query {}", query);
        return tourLogService.searchLogsByText(tourId, query);
    }
}
