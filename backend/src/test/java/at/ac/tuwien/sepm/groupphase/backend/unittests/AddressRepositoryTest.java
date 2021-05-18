package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest implements TestData {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void givenNothing_whenSaveAddress_thenFindListWithOneAddressAndFindElementById() {
        Address address = new Address(0L,TEST_ADDRESS_STREET,TEST_ADDRESS_POSTALCODE,TEST_ADDRESS_HOUSENUMBER,0,"0");
        addressRepository.save(address);
        assertAll(
            () -> assertEquals(1, addressRepository.findAll().size()),
            () -> assertNotNull(addressRepository.findById(address.getId()))
        );
    }
}
