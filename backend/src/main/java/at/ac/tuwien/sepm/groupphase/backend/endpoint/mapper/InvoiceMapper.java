package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleInvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InvoiceMapper {
    @Named("invoice")
    Invoice detailedInvoiceDtoToInvoice (DetailedInvoiceDto detailedInvoiceDTO);
    DetailedInvoiceDto invoiceToDetailedInvoiceDto (Invoice invoice);
    List<SimpleInvoiceDto> invoiceToSimpleInvoiceDto (List<Invoice> invoice);

}
