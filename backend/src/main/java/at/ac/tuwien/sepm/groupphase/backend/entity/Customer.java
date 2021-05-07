package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.validation.constraints.NotNull;

public class Customer {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String loginName;
    private Long address;
    public Customer() {
    }

    public Customer(String email, String password, String name, String loginName, Long address, Long id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
        this.address = address;
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

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }
}
