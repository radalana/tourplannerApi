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


    @Query("SELECT log FROM TourLog log WHERE log.tour.id = :tourId AND " +
            "LOWER(log.comment) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<TourLog> searchLogsByComment(@Param("tourId") Long tourId, @Param("searchText") String searchText);


    /*
    @Query("SELECT l FROM TourLog l WHERE l.tour.id = :tourId AND " +
            "(LOWER(l.comment) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(l.date) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            ")")
    List<TourLog> searchLogsByTourAndAnyField(@Param("tourId") Long tourId,
                                              @Param("searchTerm") String searchTerm);
     */
}
