package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;


import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ConfirmedPaymentDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ConfirmedPayment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PaymentMapper {
    ConfirmedPayment confirmedPaymentDtoToConfirmedPayment(ConfirmedPaymentDto paymentDto);

    ConfirmedPaymentDto confirmedPaymentToConfirmedPaymentDto(ConfirmedPayment payment);
}
