package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class CancellationPeriodDto {
    private int days;

    public CancellationPeriodDto() {
    }

    ;

    public CancellationPeriodDto(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
