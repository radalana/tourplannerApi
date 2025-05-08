package at.technikum_wien.tourplannerapi.repository;

import at.technikum_wien.tourplannerapi.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query("SELECT t FROM Tour t WHERE " +
            "LOWER(t.tourName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.fromLocation) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.toLocation) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Tour> searchTours(@Param("query") String query);
}
