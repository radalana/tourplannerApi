package at.technikum_wien.tourplannerapi.service;

import at.technikum_wien.tourplannerapi.dto.TourDTO;
import at.technikum_wien.tourplannerapi.dto.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.exception.ResourceNotFoundExeption;
import at.technikum_wien.tourplannerapi.mapper.TourMapper;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TourServiceTest {

    private TourRepository tourRepository;
    private TourLogService tourLogService;
    private TourMapper tourMapper;
    private TourService tourService;

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
        tourLogService = mock(TourLogService.class);
        tourMapper = mock(TourMapper.class);
        tourService = new TourService();

        //because TourService has private fields, we use reflection to set them
        setPrivateField(tourService, "repository", tourRepository);
        setPrivateField(tourService, "tourLogService", tourLogService);
        setPrivateField(tourService, "tourMapper", tourMapper);
    }

    // Test 1: getTourById - Tour exists
    @Test
    void testGetTourById_whenTourExists_returnsTour() {
        Tour mockTour = new Tour();
        mockTour.setId(1L);
        when(tourRepository.findById(1L)).thenReturn(Optional.of(mockTour));

        Tour result = tourService.getTourById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tourRepository).findById(1L);
    }

    // Test 2: updateTour - success
    @Test
    void testUpdateTour_whenTourExists_updatesAndReturnsDTO() throws ResourceNotFoundExeption {
        TourUpdateDTO updateDTO = new TourUpdateDTO();
        Tour existingTour = new Tour();
        existingTour.setId(2L);
        TourDTO mappedDTO = new TourDTO();

        when(tourRepository.findById(2L)).thenReturn(Optional.of(existingTour));
        when(tourMapper.map(existingTour)).thenReturn(mappedDTO);

        TourDTO result = tourService.updateTour(2L, updateDTO);

        assertNotNull(result);
        verify(tourMapper).update(updateDTO, existingTour);
        verify(tourRepository).save(existingTour);
    }

    // Test 3: searchToursByText - filters results
    @Test
    void testSearchToursByText_returnsMappedDTOs() {
        Tour t1 = new Tour();
        Tour t2 = new Tour();
        List<Tour> mockResult = Arrays.asList(t1, t2);

        when(tourRepository.searchTours("alps")).thenReturn(mockResult);
        when(tourMapper.map(any(Tour.class))).thenReturn(new TourDTO());

        List<TourDTO> result = tourService.searchToursByText("alps");

        assertEquals(2, result.size());
        verify(tourRepository).searchTours("alps");
        verify(tourMapper, times(2)).map(any(Tour.class));
    }

    // Test 4: getAllTours - returns all tours
    @Test
    void testGetAllTours_returnsAllTours() {
        List<Tour> mockTours = Arrays.asList(new Tour(), new Tour());
        when(tourRepository.findAll()).thenReturn(mockTours);

        Iterable<Tour> result = tourService.getAllTours();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
        assertEquals(2, ((List<?>) result).size());
        verify(tourRepository).findAll();
    }

    // Test 5: deleteTour - removes tour by ID
    @Test
    void testDeleteTour_deletesById() {
        Long tourId = 10L;

        tourService.deleteTour(tourId);

        verify(tourRepository).deleteById(tourId);
    }
}
