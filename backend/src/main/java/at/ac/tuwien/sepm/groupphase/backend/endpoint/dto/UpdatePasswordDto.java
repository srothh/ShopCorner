package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UpdatePasswordDto {

    @NotNull(message = "Altes Passwort darf nicht null sein")
    @NotBlank(message = "Altes Passwort darf nicht leer sein")
    @Size(min = 8, message = "Altes Passwort muss mindestens aus 8 Zeichen bestehen.")
    private String oldPassword;
    @NotNull(message = "Neues Passwort darf nicht null sein")
    @NotBlank(message = "Neues Passwort darf nicht leer sein")
    @Size(min = 8, message = "Neues Passwort muss mindestens aus 8 Zeichen bestehen.")
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
