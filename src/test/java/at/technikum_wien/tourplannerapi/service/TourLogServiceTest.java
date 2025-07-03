package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.log.TourLogDTO;
import at.technikum_wien.tourplannerapi.dto.log.TourLogUpdateDTO;
import at.technikum_wien.tourplannerapi.mapper.TourLogMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourLogServiceTest {

    private TourLogService tourLogService;

    private TourLogMapper tourLogMapper;
    private TourLogRepository tourLogRepository;
    private TourRepository tourRepository;

    @BeforeEach
    void setUp() throws Exception {
        tourLogMapper = mock(TourLogMapper.class);
        tourLogRepository = mock(TourLogRepository.class);
        tourRepository = mock(TourRepository.class);

        tourLogService = new TourLogService();

        // Inject all dependencies via reflection
        injectPrivateField(tourLogService, "tourLogMapper", tourLogMapper);
        injectPrivateField(tourLogService, "tourLogRepository", tourLogRepository);
        injectPrivateField(tourLogService, "tourRepository", tourRepository);
    }

    private void injectPrivateField(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    // Test 1: fetches and maps tour logs
    @Test
    void testFetchLogsByTourId_returnsMappedList() {
        Long tourId = 1L;
        TourLog log1 = new TourLog();
        TourLog log2 = new TourLog();
        List<TourLog> logs = List.of(log1, log2);

        TourLogDTO dto1 = new TourLogDTO();
        TourLogDTO dto2 = new TourLogDTO();

        when(tourLogRepository.findByTourId(tourId)).thenReturn(logs);
        when(tourLogMapper.map(log1)).thenReturn(dto1);
        when(tourLogMapper.map(log2)).thenReturn(dto2);

        List<TourLogDTO> result = tourLogService.fetchLogsByTourId(tourId);

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    // Test 2: saves log and returns DTO
    @Test
    void testSaveLog_savesAndReturnsDto() {
        Long tourId = 1L;
        Tour tour = new Tour();
        tour.setId(tourId);

        TourLogDTO inputDto = new TourLogDTO();
        TourLog inputLog = new TourLog();
        inputLog.setTour(tour);

        TourLog savedLog = new TourLog();
        TourLogDTO expectedDto = new TourLogDTO();

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));
        when(tourLogMapper.map(inputDto)).thenReturn(inputLog);
        when(tourLogRepository.save(inputLog)).thenReturn(savedLog);
        when(tourLogMapper.map(savedLog)).thenReturn(expectedDto);

        TourLogDTO result = tourLogService.saveLog(inputDto, tourId);

        assertEquals(expectedDto, result);
    }

    // Test 3: throws BadRequestException if tour not found
    @Test
    void testUpdateTourLog_invalidTourId_throwsBadRequestException() {
        Long tourId = 1L;
        Long logId = 2L;

        TourLog existingLog = new TourLog();
        Tour wrongTour = new Tour();
        wrongTour.setId(999L);  //mismatched tour ID
        existingLog.setTour(wrongTour);

        when(tourLogRepository.findById(logId)).thenReturn(Optional.of(existingLog));

        TourLogUpdateDTO updateDTO = new TourLogUpdateDTO();

        assertThrows(BadRequestException.class, () ->
                tourLogService.updateTourLog(tourId, logId, updateDTO));
    }

    // Test 4: deletes log successfully
    @Test
    void testDeleteLog_validLog_deletesSuccessfully() throws BadRequestException {
        Long tourId = 1L;
        Long logId = 10L;

        Tour tour = new Tour();
        tour.setId(tourId);

        TourLog log = new TourLog();
        log.setId(logId);
        log.setTour(tour);

        when(tourLogRepository.findById(logId)).thenReturn(Optional.of(log));

        // deletion
        tourLogService.deleteLog(logId, tourId);

        verify(tourLogRepository).deleteById(logId);
    }
}
