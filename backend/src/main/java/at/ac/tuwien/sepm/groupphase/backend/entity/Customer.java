package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    @NotBlank
    @Max(value = 255, message = "Name must not be longer than 255 characters")
    private String name;
    @Column(name = "login_name", nullable = false)
    @NotBlank
    @Max(value = 128, message = "Login name must not be longer than 128 characters")
    private String loginName;
    @Column(name = "password", nullable = false)
    @NotBlank
    @Max(value = 128, message = "Password must not be longer than 128 characters")
    private String password;
    @Column(name = "email", nullable = false)
    @Max(value = 255, message = "Email must not be longer than 255 characters")
    @NotBlank
    @Email
    private String email;
    //TODO: add relation to address
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    @Positive
    private Long address;
    @Column(name = "phone_number")
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String email, String password, String name, String loginName, Long address, Long id, String phoneNumber) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
