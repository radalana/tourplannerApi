package at.technikum_wien.tourplannerapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TourUpdateDTO {
    private String tourName;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String transportType;
    private double distance;
    private double estimatedTime;
}
