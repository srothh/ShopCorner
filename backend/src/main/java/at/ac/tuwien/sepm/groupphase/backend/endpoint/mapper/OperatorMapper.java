package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OverviewOperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


import java.util.List;

@Mapper
public interface OperatorMapper {

    @Named("overviewOperator")
    OverviewOperatorDto operatorToOverviewOperatorDto(Operator operator);

    @IterableMapping(qualifiedByName = "overviewOperator")
    List<OverviewOperatorDto> operatorToOverviewOperatorDto(List<Operator> operator);

}
