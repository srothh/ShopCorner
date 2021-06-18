package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CanceledInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CanceledInvoice;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CanceledInvoiceMapper {

    CanceledInvoice canceledInvoiceDtoToCanceledInvoice(CanceledInvoiceDto canceledInvoiceDto);

    @Named("canceledInvoice")
    CanceledInvoiceDto canceledInvoiceToCanceledInvoiceDto(CanceledInvoice canceledInvoice);

    @IterableMapping(qualifiedByName = "canceledInvoice")
    List<CanceledInvoiceDto> canceledInvoiceToCanceledInvoiceDto(List<CanceledInvoice> canceledInvoice);
}
