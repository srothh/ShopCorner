package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OperatorMapper {

    OperatorDto entityToDto(Operator operator);

    Operator dtoToEntity(OperatorDto operatorDto);
}
