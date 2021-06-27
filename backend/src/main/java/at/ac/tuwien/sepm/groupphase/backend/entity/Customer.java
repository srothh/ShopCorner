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
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name darf nicht leer sein")
    private String name;
    @Column(name = "login_name", nullable = false, length = 128, unique = true)
    @NotBlank(message = "LoginName darf nicht leer sein")
    private String loginName;
    @Column(name = "password", nullable = false, length = 60)
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "EMail darf nicht leer sein")
    @Email(message = "Ungültiges E-Mail-Format")
    private String email;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false, referencedColumnName = "id", updatable = false)
    private Address address;
    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean deleted;



    public Customer() {
    }

    public Customer(String email, String password, String name, String loginName, Address address, Long id, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
        this.address = address;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String email, String password, String name, String loginName, Address address, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(password, customer.password) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address)
            && Objects.equals(phoneNumber, customer.phoneNumber);
    }
}
