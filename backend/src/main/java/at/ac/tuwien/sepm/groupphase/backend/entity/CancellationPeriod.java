package at.ac.tuwien.sepm.groupphase.backend.entity;

public class CancellationPeriod {
    private int days;

    public CancellationPeriod() {
    }

    ;

    public CancellationPeriod(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
