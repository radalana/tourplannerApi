package at.technikum_wien.tourplannerapi.config;
import at.technikum_wien.tourplannerapi.model.Tour;
import at.technikum_wien.tourplannerapi.repository.TourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner seedDatabase(TourRepository tourRepository) {
        return args -> {
            if (tourRepository.count() == 0) {
                Tour t1 = new Tour("Wien Stadttour", "Rundfahrt", "Stephansplatz", "Schönbrunn", 5.2, 1.5, "WALK");
                Tour t2 = new Tour("Alpenfahrt", "Alpen-Tour", "Innsbruck", "Salzburg", 350, 4.0, "CAR");

                tourRepository.save(t1);
                tourRepository.save(t2);

                System.out.println("✅ Seeded initial tours.");
            }
        };
    }
}
