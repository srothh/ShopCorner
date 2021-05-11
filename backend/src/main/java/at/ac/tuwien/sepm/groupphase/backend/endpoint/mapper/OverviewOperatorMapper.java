package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.OverviewOperator;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper
public interface OverviewOperatorMapper {

    List<OverviewOperatorDto> overviewOperatorToDto(List<OverviewOperator> overviewOperators);

}
