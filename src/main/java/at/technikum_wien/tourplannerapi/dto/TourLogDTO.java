package at.technikum_wien.tourplannerapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TourLogDTO {
    private Long id;
    private String date;
    private String comment;
    private int difficulty;
    private double totalDistance;
    private double totalDuration;
    private int rating;
}
