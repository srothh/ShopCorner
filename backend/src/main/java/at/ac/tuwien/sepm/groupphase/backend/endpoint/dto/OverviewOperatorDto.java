package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class OverviewOperatorDto {

    private Long id;

    @NotNull(message = "Name darf nicht null sein")
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull(message = "Benutzername darf nicht null sein")
    @NotBlank
    @Size(max = 128)
    private String loginName;

    @NotNull(message = "Email darf nicht null sein")
    @NotBlank
    @Email
    private String email;

    @NotNull(message = "Berechtigungslevel darf nicht null sein")
    @Enumerated(EnumType.STRING)
    private Permissions permissions;

    public OverviewOperatorDto(){
    }

    public OverviewOperatorDto(long id, String name, String loginName, String email, Permissions permissions) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.email = email;
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof OverviewOperatorDto)) {
            return false;
        }
        OverviewOperatorDto overviewOperatorDto = (OverviewOperatorDto) o;
        return Objects.equals(id, overviewOperatorDto.id)
            && Objects.equals(name, overviewOperatorDto.name)
            && Objects.equals(loginName, overviewOperatorDto.loginName)
            && Objects.equals(email, overviewOperatorDto.email)
            && Objects.equals(permissions, overviewOperatorDto.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, loginName, email, permissions);
    }

    @Override
    public String toString() {
        return "OverviewOperatorDto{"
            + "id='" + id + '\''
            + ", name='" + name + '\''
            + ", login_name='" + loginName + '\''
            + ", email='" + email + '\''
            + ", permissions='" + permissions + '\''
            + '}';
    }
}
