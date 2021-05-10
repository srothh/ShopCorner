package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InvoiceMapper {

    @Named("simpleInvoice")
    SimpleInvoiceDto invoiceToSimpleInvoiceDto(Invoice invoice);


    @IterableMapping(qualifiedByName = "simpleInvoice")
    List<SimpleInvoiceDto> invoiceToSimpleInvoiceDto (List<Invoice> invoice);

    DetailedInvoiceDto invoiceToDetailedInvoiceDto (Invoice invoice);


}
