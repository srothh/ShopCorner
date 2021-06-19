package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CancellationPeriodDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CancellationPeriod;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CancellationPeriodMapper {
    public CancellationPeriod cancellationPeriodDtoToCancellationPeriod(CancellationPeriodDto dto);

    public CancellationPeriodDto cancellationPeriodToCancellationPeriodDto(CancellationPeriod cancellationPeriod);
}
