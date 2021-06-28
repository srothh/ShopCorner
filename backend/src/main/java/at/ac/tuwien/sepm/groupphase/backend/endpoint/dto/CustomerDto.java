package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDto {
    private Long id;
    @NotNull(message = "E-Mail darf nicht null sein")
    @Email(message = "Ungültiges E-Mail-Format")
    private String email;
    @NotNull(message = "Name darf nicht null sein")
    @Size(max = 255, message = "Name darf nicht länger als 255 Zeichen sein")
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;
    @NotNull(message = "LoginName darf nicht null sein")
    @NotBlank(message = "LoginName darf nicht leer sein")
    @Size(max = 128, message = "LoginName darf nicht länger als 128 Zeichen sein")
    private String loginName;
    @Valid
    private AddressDto address;

    private String phoneNumber;
    private boolean deleted;



    public CustomerDto() {
    }

    public CustomerDto(Long id, String name, String loginName, String email, AddressDto address, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.loginName = loginName;
        this.address = address;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
