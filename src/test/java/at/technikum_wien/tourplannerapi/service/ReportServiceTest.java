package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    private TourRepository tourRepository;
    private TourLogRepository tourLogRepository;
    private ReportService reportService;

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("failed to set field: " + fieldName, e);
        }
    }

    @BeforeEach
    void setup() {
        tourRepository = mock(TourRepository.class);
        tourLogRepository = mock(TourLogRepository.class);
        reportService = new ReportService();

        setPrivateField(reportService, "tourRepository", tourRepository);
        setPrivateField(reportService, "tourLogRepository", tourLogRepository);
    }

    // Test 1: generateSummaryReport produces non-empty PDF
    @Test
    void testGenerateSummaryReport_returnsPdfBytes() {
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setTourName("Alps");

        TourLog log = new TourLog();
        log.setTotalDistance(10.0);
        log.setTotalDuration(1.0);
        log.setRating(5);

        when(tourRepository.findAll()).thenReturn(List.of(tour));
        when(tourLogRepository.findByTourId(1L)).thenReturn(List.of(log));

        byte[] result = reportService.generateSummaryReport();

        assertNotNull(result);
        assertTrue(result.length > 0);
        verify(tourRepository).findAll();
    }

    // Test 2: generateTourReport returns valid report for existing tour
    @Test
    void testGenerateTourReport_existingTour_returnsPdfBytes() {
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setTourName("Danube Tour");
        tour.setDescription("Along the river");
        tour.setFromLocation("Vienna");
        tour.setToLocation("Linz");
        tour.setTransportType("Bike");
        tour.setDistance(80.0);
        tour.setEstimatedTime((long)35);

        TourLog log = new TourLog();
        log.setComment("Nice trip!");
        log.setDate(java.time.LocalDate.now());
        log.setDifficulty(2);
        log.setTotalDuration(3.0);
        log.setRating(4);

        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));
        when(tourLogRepository.findByTourId(1L)).thenReturn(List.of(log));

        byte[] result = reportService.generateTourReport(1L);

        assertNotNull(result);
        assertTrue(result.length > 0);
        verify(tourRepository).findById(1L);
        verify(tourLogRepository).findByTourId(1L);
    }

    // Test 3: generateTourReport throws when tour not found
    @Test
    void testGenerateTourReport_tourNotFound_throwsException() {
        when(tourRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () ->
                reportService.generateTourReport(99L));

        assertEquals("Tour not found", ex.getMessage());
    }
}
