package at.technikum_wien.tourplannerapi.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "tours")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tour {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String tourName;
    private String description;
    @Column(name = "from_location")
    @JsonProperty("from")
    private String fromLocation;

    @Column(name = "to_location")
    @JsonProperty("to")
    private String toLocation;
    private double distance;
    private double estimatedTime;
    private String transportType;
    // private String imagePath; --later for map

    @Transient
    private int popularity;
    @Transient
    private double childFriendliness;

    //links a tour to its logs -> bi-directional relationship
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourLog> tourLogs;
    //till there are no dto
    public Tour(String tourName, String description, String fromLocation, String toLocation,
                double distance, double estimatedTime, String transportType) {
        this.tourName = tourName;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.transportType = transportType;
    }

    public Tour() {

    }
}
