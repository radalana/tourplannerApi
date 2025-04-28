package at.technikum_wien.tourplannerapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tour_logs")
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private String comment;
    private String difficulty;
    private double totalDistance;
    private double totalTime;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    //.....json ignore https://www.baeldung.com/java-jsonignore-vs-transient
    private Tour tour;
}
