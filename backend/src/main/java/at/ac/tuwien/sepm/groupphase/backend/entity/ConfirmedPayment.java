package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

@Entity
@Table(name = "ConfirmedPayment")
public class ConfirmedPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentId;
    private String payerId;

    public ConfirmedPayment(Long id, String paymentId, String payerId) {
        this.id = id;
        this.paymentId = paymentId;
        this.payerId = payerId;
    }

    public ConfirmedPayment(){}

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

    @Override
    public String toString() {
        return "ConfirmedPayment{"
            +
            "id=" + id
            +
            ", paymentId='" + paymentId + '\''
            +
            ", payerId='" + payerId + '\''
            +
            '}';
    }
}
