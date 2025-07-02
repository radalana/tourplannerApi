package at.technikum_wien.tourplannerapi.dto.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourLogUpdateDTO {
    @NotNull
    private String date;

    @NotBlank
    private String comment;

    @Positive
    private int difficulty;

    @Positive
    private double totalDistance;

    @Positive
    private double totalDuration;

    @Positive
    private int rating;
}
