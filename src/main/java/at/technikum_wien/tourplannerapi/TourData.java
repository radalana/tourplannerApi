package at.technikum_wien.tourplannerapi;

import at.technikum_wien.tourplannerapi.model.Tour;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class TourData {

    public static List<Tour> getMockTours() {
        return Arrays.asList(
                new Tour(1L, "Mock Tour", "Postman mock", "Vienna", "Bratislava", "Train", 60.0, Duration.ofHours(1), "Test route"),
                new Tour(2L, "Mock Tour 2", "Generated tour", "Berlin", "Munich", "Car", 585.0, Duration.ofHours(6), "Autobahn A9"),
                new Tour(3L, "Mock Tour 3", "Generated tour", "Paris", "Lyon", "Train", 465.0, Duration.ofHours(2), "TGV Route"),
                new Tour(4L, "Mock Tour 4", "Generated tour", "Rome", "Naples", "Bus", 225.0, Duration.ofMinutes(150), "A1 Highway"),
                new Tour(5L, "Mock Tour 5", "Generated tour", "Amsterdam", "Rotterdam", "Train", 80.0, Duration.ofHours(1), "NS Intercity"),
                new Tour(6L, "Mock Tour 6", "Generated tour", "Madrid", "Barcelona", "Plane", 620.0, Duration.ofMinutes(72), "Domestic flight"),
                new Tour(7L, "Mock Tour 7", "Generated tour", "Prague", "Brno", "Bus", 210.0, Duration.ofMinutes(138), "D1 Route"),
                new Tour(8L, "Mock Tour 8", "Generated tour", "Lisbon", "Porto", "Train", 313.0, Duration.ofHours(3), "Alfa Pendular"),
                new Tour(9L, "Mock Tour 9", "Generated tour", "Oslo", "Bergen", "Train", 496.0, Duration.ofHours(7), "Scenic route"),
                new Tour(10L, "Mock Tour 10", "Generated tour", "Stockholm", "Gothenburg", "Car", 470.0, Duration.ofHours(5), "E4 Highway"),
                new Tour(11L, "Mock Tour 11", "Generated tour", "London", "Manchester", "Train", 335.0, Duration.ofMinutes(150), "Avanti West Coast"),
                new Tour(12L, "Mock Tour 12", "Generated tour", "Brussels", "Antwerp", "Bus", 45.0, Duration.ofHours(1), "Local line"),
                new Tour(13L, "Mock Tour 13", "Generated tour", "Zurich", "Geneva", "Train", 278.0, Duration.ofMinutes(165), "Swiss Rail"),
                new Tour(14L, "Mock Tour 14", "Generated tour", "Copenhagen", "Aarhus", "Car", 310.0, Duration.ofMinutes(210), "E45 Highway"),
                new Tour(15L, "Mock Tour 15", "Generated tour", "Athens", "Thessaloniki", "Train", 500.0, Duration.ofHours(5), "Greek Rail"),
                new Tour(16L, "Mock Tour 16", "Generated tour", "Warsaw", "Krakow", "Train", 300.0, Duration.ofMinutes(150), "PKP Intercity"),
                new Tour(17L, "Mock Tour 17", "Generated tour", "Helsinki", "Tampere", "Train", 180.0, Duration.ofMinutes(90), "VR Line"),
                new Tour(18L, "Mock Tour 18", "Generated tour", "Budapest", "Debrecen", "Bus", 230.0, Duration.ofMinutes(150), "M3 Highway"),
                new Tour(19L, "Mock Tour 19", "Generated tour", "Sofia", "Plovdiv", "Train", 150.0, Duration.ofHours(2), "BDZ route"),
                new Tour(20L, "Mock Tour 20", "Generated tour", "Ljubljana", "Maribor", "Car", 130.0, Duration.ofMinutes(90), "A1 Route"),
                new Tour(21L, "Mock Tour 21", "Generated tour", "Zagreb", "Split", "Train", 410.0, Duration.ofHours(6), "Croatian Railways"),
                new Tour(22L, "Mock Tour 22", "Generated tour", "Belgrade", "Novi Sad", "Bus", 95.0, Duration.ofMinutes(72), "Serbian Route"),
                new Tour(23L, "Mock Tour 23", "Generated tour", "Skopje", "Ohrid", "Car", 170.0, Duration.ofMinutes(150), "M4 Highway"),
                new Tour(24L, "Mock Tour 24", "Generated tour", "Tirana", "Shkoder", "Bus", 100.0, Duration.ofHours(2), "SH1 Route"),
                new Tour(25L, "Mock Tour 25", "Generated tour", "Sarajevo", "Mostar", "Train", 130.0, Duration.ofHours(2), "Bosnian Rail"),
                new Tour(26L, "Mock Tour 26", "Generated tour", "Podgorica", "Bar", "Train", 50.0, Duration.ofHours(1), "Montenegro Rail")
        );
    }
}
