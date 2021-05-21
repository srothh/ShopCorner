package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceItemDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;

@Mapper
public interface InvoiceItemMapper {
    @Named("toEntity")
    InvoiceItem dtoToEntity(InvoiceItemDto invoiceItemDto);

    @IterableMapping(qualifiedByName = "toEntity")
    Set<InvoiceItem> dtoToEntity(Set<InvoiceItemDto> invoiceItemDtoSet);
}
