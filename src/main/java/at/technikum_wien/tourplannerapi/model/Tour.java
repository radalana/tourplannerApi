package at.technikum_wien.tourplannerapi.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private String from;
    private String to;
    private String transportType;

}
