package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UpdatePasswordDto {

    @NotNull(message = "Altes passwort darf nicht null sein")
    @NotBlank(message = "Altes passwort darf nicht leer sein")
    private String oldPassword;
    @NotNull(message = "Neues passwort darf nicht null sein")
    @NotBlank(message = "Neues passwort darf nicht leer sein")
    private String newPassword;

    public UpdatePasswordDto(){}

    public UpdatePasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
