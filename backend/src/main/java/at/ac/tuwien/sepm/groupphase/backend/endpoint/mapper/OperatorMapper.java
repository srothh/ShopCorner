package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OperatorDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;

import org.springframework.stereotype.Component;

@Component
public class OperatorMapper {

    public OperatorDto entityToDto(Operator operator) {
        if (operator == null) { return null; }

        return new OperatorDto(operator.getId(), operator.getName(), operator.getLogin_name(), operator.getPassword(),
            operator.getEmail(), operator.getPermissions());
    }

    public Operator dtoToEntity(OperatorDto operatorDto){

        if(operatorDto == null) { return null; }

        return new Operator(operatorDto.getId(), operatorDto.getName(), operatorDto.getLogin_name(), operatorDto.getPassword(),
            operatorDto.getEmail(), operatorDto.getPermissions());
    }
}
