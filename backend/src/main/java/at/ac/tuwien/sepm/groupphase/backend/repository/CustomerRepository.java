package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "INSERT INTO customer(id,name,login_name,password,email,address,phone_number) VALUES :id,:name,:login_name,:password,:email,:address_id,:phone_number", nativeQuery = true)
    Customer registerNewCustomer(@Param("id") Long id, @Param("name") String name, @Param("login_name") String loginName, @Param("password") String password, @Param("email") String email, @Param("address_id") Long address,
                                 @Param("phone_number") String phoneNumber);
}
