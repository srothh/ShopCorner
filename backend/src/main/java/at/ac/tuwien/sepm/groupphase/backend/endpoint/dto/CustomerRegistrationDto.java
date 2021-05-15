package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRegistrationDto {

    private Long id;
    @NotNull(message = "email must not be null")
    @Email
    @Size(max = 255, message = "House number must not have more than 64 Characters")
    private String email;
    //@NotNull(message = "password must not be null")
    //@NotBlank
    private String password;
    @NotNull(message = "Name must not be null")
    @Size(max = 255, message = "House number must not have more than 64 Characters")
    @NotBlank
    private String name;
    @NotNull(message = "loginName must not be null")
    @NotBlank
    @Size(max = 128, message = "House number must not have more than 64 Characters")
    private String loginName;
    private AddressDto address;
    @Size(max = 128, message = "House number must not have more than 64 Characters")
    private String phoneNumber;

    public CustomerRegistrationDto() {
    }

    public CustomerRegistrationDto(Long id, String name, String loginName, String password, String email, AddressDto address, String phoneNumber) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
