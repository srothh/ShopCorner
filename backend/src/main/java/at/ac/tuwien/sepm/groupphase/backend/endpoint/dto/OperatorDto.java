package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class OperatorDto {

    private Long id;

    @NotNull(message = "Name darf nicht null sein")
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 255, message = "Name darf nicht länger als 255 Zeichen sein")
    private String name;

    @NotNull(message = "LoginName darf nicht null sein")
    @NotBlank(message = "LoginName darf nicht leer sein")
    @Size(max = 128, message = "LoginName darf nicht länger als 128 Zeichen sein")
    private String loginName;

    @NotNull(message = "Passwort darf nicht null sein")
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;

    @NotNull(message = "E-Mail darf nicht null sein")
    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "Ungültiges E-Mail-Format")
    private String email;

    @NotNull(message = "Berechtigungslevel darf nicht null sein")
    @Enumerated(EnumType.STRING)
    private Permissions permissions;


    public OperatorDto(){}

    public OperatorDto(Long id, String name, String loginName, String password, String email, Permissions permissions) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.permissions = permissions;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return this.loginName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permissions getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatorDto)) {
            return false;
        }
        OperatorDto operatorLoginDto = (OperatorDto) o;
        return Objects.equals(id, operatorLoginDto.id)
            && Objects.equals(name, operatorLoginDto.name)
            && Objects.equals(loginName, operatorLoginDto.loginName)
            && Objects.equals(email, operatorLoginDto.email)
            && Objects.equals(password, operatorLoginDto.password)
            && Objects.equals(permissions, operatorLoginDto.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, loginName, email, password, permissions);
    }

    @Override
    public String toString() {
        return "OperatorDto{"
            + "id='" + id + '\''
            + ", name='" + name + '\''
            + ", login_name='" + loginName + '\''
            + ", email='" + email + '\''
            + ", password='" + password + '\''
            + ", permissions='" + permissions + '\''
            + '}';
    }


}
