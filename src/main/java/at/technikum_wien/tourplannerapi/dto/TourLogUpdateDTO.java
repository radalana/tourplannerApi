package at.technikum_wien.tourplannerapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourLogUpdateDTO {
    private String date;
    private String comment;
    private int difficulty;
    private double totalDistance;
    private double totalDuration;
    private int rating;
}
