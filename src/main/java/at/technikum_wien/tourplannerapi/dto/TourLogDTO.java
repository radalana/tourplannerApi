package at.technikum_wien.tourplannerapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TourLogDTO {
    private Long id;
    @Past
    @NotNull
    private String date;

    @NotNull
    @NotEmpty
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
