package at.technikum_wien.tourplannerapi.repository;

import at.technikum_wien.tourplannerapi.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TourRepository extends JpaRepository<Tour, Long> {
}
