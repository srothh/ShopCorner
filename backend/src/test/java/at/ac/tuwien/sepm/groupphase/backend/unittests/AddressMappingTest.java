package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CustomerRegistrationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddressMappingTest implements TestData {
    private final Address address = new Address(0L,TEST_ADDRESS_STREET,TEST_ADDRESS_POSTALCODE,TEST_ADDRESS_HOUSENUMBER,0,"0");
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void givenNothing_whenMapAddressDtoToEntity_thenEntityHasAllProperties() {
        AddressDto addressDto = addressMapper.addressToAddressDto(address);
        assertAll(
            () -> assertEquals(0, addressDto.getId()),
            () -> assertEquals(TEST_ADDRESS_STREET, addressDto.getStreet()),
            () -> assertEquals(TEST_ADDRESS_POSTALCODE, addressDto.getPostalCode()),
            () -> assertEquals(TEST_ADDRESS_HOUSENUMBER, addressDto.getHouseNumber())
        );
    }

    @Test
    public void givenNothing_whenMapListWithTwoAddressEntitiesToDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        addresses.add(address);

        List<AddressDto> addressDtos = addressMapper.addressListToAddressDtoList(addresses);
        assertEquals(2, addressDtos.size());
        AddressDto addressDto = addressDtos.get(0);
        assertAll(
            () -> assertEquals(0, addressDto.getId()),
            () -> assertEquals(TEST_ADDRESS_STREET, addressDto.getStreet()),
            () -> assertEquals(TEST_ADDRESS_POSTALCODE, addressDto.getPostalCode()),
            () -> assertEquals(TEST_ADDRESS_HOUSENUMBER, addressDto.getHouseNumber())
        );
    }
}
