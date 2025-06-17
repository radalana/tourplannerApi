package at.technikum_wien.tourplannerapi.dto;

import java.util.List;

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
}
