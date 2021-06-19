package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ConfirmedPaymentDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentId;
    private String payerId;

    public ConfirmedPaymentDto(){}

    public ConfirmedPaymentDto(Long id, String paymentId, String payerId) {
        this.id = id;
        this.paymentId = paymentId;
        this.payerId = payerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
}
