package at.technikum_wien.tourplannerapi.config;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner seedDatabase(TourRepository tourRepository) {
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

            System.out.println("✅ Seeded 7 initial tours.");
            } else {
                System.out.println("⚠️ Database already contains tours-skipping seeding.");
            }
        };
    }
}
