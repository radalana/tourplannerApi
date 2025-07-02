package at.technikum_wien.tourplannerapi.mapper;

import at.technikum_wien.tourplannerapi.dto.tour.TourDTO;
import at.technikum_wien.tourplannerapi.dto.tour.TourUpdateDTO;
import at.technikum_wien.tourplannerapi.model.Tour;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TourMapper {
    public abstract void update(TourUpdateDTO dto, @MappingTarget Tour model);
    public abstract TourDTO map(Tour model);
    public abstract Tour map(TourDTO dto); //for importing tours from file
}

