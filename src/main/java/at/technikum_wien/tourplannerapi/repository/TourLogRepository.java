package at.technikum_wien.tourplannerapi.repository;

import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TourLogRepository extends JpaRepository<TourLog, Long> {
    int countTourLogsByTour(Tour tour); //for popularity
    List<TourLog> getAllLogsByTour(Tour tour); //for child-friendliness

    List<TourLog> findByTourId(Long tourId);

    @Query("SELECT log FROM TourLog log WHERE LOWER(log.comment) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<TourLog> searchLogsByComment(@Param("searchText") String searchText);
}
