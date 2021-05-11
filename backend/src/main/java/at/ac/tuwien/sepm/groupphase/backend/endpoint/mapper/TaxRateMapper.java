package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaxRateDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import org.mapstruct.Mapper;

@Mapper
public interface TaxRateMapper {
    TaxRateDto entityToDto(TaxRate taxRate);

    TaxRate dtoToEntity(TaxRateDto productDto);
}
