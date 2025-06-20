package at.technikum_wien.tourplannerapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tour_logs")
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String comment;
    private int difficulty;
    private double totalDistance;
    private double totalDuration;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    @JsonBackReference
    //.....json ignore https://www.baeldung.com/java-jsonignore-vs-transient
    private Tour tour;
}
