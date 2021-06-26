package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;

public class CancellationPeriod {
    private int days;

    public CancellationPeriod() {
    }

    public CancellationPeriod(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CancellationPeriod cancellationPeriod = (CancellationPeriod) o;
        return Objects.equals(this.days, cancellationPeriod.days);
    }
}
