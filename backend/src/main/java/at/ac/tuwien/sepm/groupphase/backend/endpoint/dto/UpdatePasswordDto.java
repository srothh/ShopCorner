package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UpdatePasswordDto {

    @NotNull(message = "oldPassword must not be null")
    @NotBlank
    private String oldPassword;
    @NotNull(message = "newPassword must not be null")
    @NotBlank
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

