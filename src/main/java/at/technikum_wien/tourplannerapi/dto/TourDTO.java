package at.technikum_wien.tourplannerapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    private double estimatedTime;
    private int popularity;
    private double childFriendliness;
    //public List<TourLogDTO> tourLogs;
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
                //", routInfo='" + getRoutInfo() + '\'' +
                ", popularity=" + getPopularity() +
                ", childFriendliness=" + getChildFriendliness() +
                //", logs=" + logs +
                '}';
    }
}
