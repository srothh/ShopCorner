package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class SimpleInvoiceDto {

    private Long id;

    @NotNull(message = "LocalDateTime can not be null")
    private LocalDateTime date;

    @DecimalMin(value = "0.01", message = "The amount can not be smaller than 0.01")
    private double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleInvoiceDto that = (SimpleInvoiceDto) o;
        return Double.compare(that.amount, amount) == 0 && id.equals(that.id) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount);
    }

    @Override
    public String toString() {
        return "SimpleInvoiceDto{" + "id=" + id + ", date=" + date + ", amount=" + amount + '}';
    }
}
