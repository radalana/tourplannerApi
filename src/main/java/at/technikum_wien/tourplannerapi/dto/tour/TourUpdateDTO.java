package at.technikum_wien.tourplannerapi.dto.tour;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TourUpdateDTO {
    @NotBlank
    private String tourName;
    @NotBlank
    private String description;
    @NotBlank
    private String fromLocation;
    @NotBlank
    private String toLocation;
    @NotBlank
    private String transportType;
    private double distance;
    private double estimatedTime;
}
