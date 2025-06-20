package at.technikum_wien.tourplannerapi.rest;

import at.technikum_wien.tourplannerapi.dto.TourLogDTO;
import at.technikum_wien.tourplannerapi.mapper.TourLogMapper;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.service.TourLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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


    /*
    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable Long id) {
        tourLogService.deleteLog(id);
    }
     */
}
