package at.technikum_wien.tourplannerapi.config;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.model.TourLog;
import at.technikum_wien.tourplannerapi.repository.TourLogRepository;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner seedDatabase(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        return args -> {
            if (tourRepository.count() == 0) {
                List<Tour> initialTours = List.of(
                        new Tour("Wien Stadttour", "Rundfahrt durch Wien", "Stephansplatz", "Schönbrunn", 5.2, 1.5, "WALK"),
                        new Tour("Alpenfahrt", "Alpen-Tour mit Ausblick", "Innsbruck", "Salzburg", 350, 4.0, "CAR"),
                        new Tour("Donau Radtour", "Fahrradtour entlang der Donau", "Linz", "Wien", 190, 3.5, "BIKE"),
                        new Tour("Historische Städte", "Besuch historischer Städte", "Graz", "Krems", 280, 4.8, "CAR"),
                        new Tour("Burgenland Route", "Wein und Burgen", "Eisenstadt", "Rust", 60, 1.2, "WALK"),
                        new Tour("Tirol Expedition", "Alpentrekking", "Kufstein", "Zell am See", 120, 2.7, "HIKE"),
                        new Tour("Seen-Tour", "Entlang der Seen Österreichs", "Villach", "Klagenfurt", 95, 2.0, "BIKE")
                );
                tourRepository.saveAll(initialTours);

                for (Tour tour : initialTours) {
                    List<TourLog> logs = List.of(
                            createLog(tour, "Sehr schöne Tour", 2, tour.getDistance(), tour.getEstimatedTime(), 4),
                            createLog(tour, "Etwas anstrengend, aber lohnend", 3, tour.getDistance(), tour.getEstimatedTime(), 5),
                            createLog(tour, "Wetter war nicht ideal", 2, tour.getDistance(), tour.getEstimatedTime(), 3)
                    );
                    tourLogRepository.saveAll(logs);
                }

                System.out.println("✅ Seeded 7 initial tours with logs.");
            } else {
                System.out.println("⚠️ Database already contains tours — skipping seeding.");
            }
        };
    }

    private TourLog createLog(Tour tour, String comment, int difficulty, double distance, double duration, int rating) {
        TourLog log = new TourLog();
        log.setDate(LocalDate.now().minusDays((int) (Math.random() * 100))); // случайная дата до 100 дней назад
        log.setComment(comment);
        log.setDifficulty(difficulty);
        log.setTotalDistance(distance);
        log.setTotalDuration(duration);
        log.setRating(rating);
        log.setTour(tour);
        return log;
    }
}
