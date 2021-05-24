package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;

import org.springframework.stereotype.Component;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
@Component
public interface OperatorMapper {

    @Named("overviewOperator")
    OverviewOperatorDto operatorToOverviewOperatorDto(Operator operator);

    @IterableMapping(qualifiedByName = "overviewOperator")
    List<OverviewOperatorDto> operatorToOverviewOperatorDto(List<Operator> operator);

    OperatorDto entityToDto(Operator operator);

    Operator dtoToEntity(OperatorDto operatorDto);
}
