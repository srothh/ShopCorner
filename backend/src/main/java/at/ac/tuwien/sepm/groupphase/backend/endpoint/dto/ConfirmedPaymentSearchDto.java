package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class ConfirmedPaymentSearchDto {
    private String payerId;
    private String paymentId;

    public ConfirmedPaymentSearchDto() {}


    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "ConfirmedPaymentSearchDto{"
            +
            "payerId='" + payerId + '\''
            +
            ", paymentId='" + paymentId + '\''
            +
            '}';
    }
}
