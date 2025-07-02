package at.technikum_wien.tourplannerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class TourDTO {
    private Long id;

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

    private long estimatedTime;
    private int popularity;
    private double childFriendliness;

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", tourName='" + getTourName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", from='" + getFromLocation() + '\'' +
                ", to='" + getToLocation() + '\'' +
                ", transportType='" + getTransportType() + '\'' +
                ", distance=" + getDistance() +
                ", estimatedTime=" + getEstimatedTime() +
                ", popularity=" + getPopularity() +
                ", childFriendliness=" + getChildFriendliness() +
                '}';
    }
}
