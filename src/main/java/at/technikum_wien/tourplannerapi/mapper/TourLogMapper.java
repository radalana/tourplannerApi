package at.technikum_wien.tourplannerapi.mapper;

import at.technikum_wien.tourplannerapi.dto.TourLogDTO;
import at.technikum_wien.tourplannerapi.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplannerapi.model.TourLog;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TourLogMapper {
    //public abstract void update(TourUpdateDTO dto, @MappingTarget Tour model);
    @Mapping(source="date", target="date", dateFormat = "dd-MM-yyyy")
    public abstract TourLogDTO map(TourLog model);

    @Mapping(source="date", target="date", dateFormat = "dd-MM-yyyy")
    public abstract TourLog map(TourLogDTO data);

    @Mapping(source="date", target="date", dateFormat = "dd-MM-yyyy")
    public abstract void update(TourLogUpdateDTO data, @MappingTarget TourLog model);
}
