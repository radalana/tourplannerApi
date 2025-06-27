package at.technikum_wien.tourplannerapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TourDTO {
    private Long id;
    private String tourName;
    private String description;
    private String fromLocation;
    private String toLocation;
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
