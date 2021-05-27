package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class OperatorPermissionChangeDto {

    @NotNull(message = "Permissions must not be null")
    @Enumerated(EnumType.STRING)
    private Permissions permissions;

    public OperatorPermissionChangeDto(){}

    public OperatorPermissionChangeDto(Permissions permissions) {
        this.permissions = permissions;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OperatorPermissionChangeDto that = (OperatorPermissionChangeDto) o;
        return permissions == that.permissions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissions);
    }

    @Override
    public String toString() {
        return "OperatorPermissionChangeDto{" + "permissions=" + permissions + '}';
    }
}
