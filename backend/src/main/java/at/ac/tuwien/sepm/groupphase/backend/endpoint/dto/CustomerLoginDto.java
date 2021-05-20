package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CustomerLoginDto {

    @NotNull(message = "Login name must not be null")
    private String loginName;

    @NotNull(message = "Password must not be null")
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerLoginDto that = (CustomerLoginDto) o;
        return Objects.equals(loginName, that.loginName) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginName, password);
    }
}