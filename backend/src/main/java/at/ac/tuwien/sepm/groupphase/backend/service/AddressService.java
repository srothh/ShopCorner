package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;

import java.util.List;

/**  A service class handling addresses.
 */
public interface AddressService {
    Address addNewAddress(Address address);

    List<Address> getAllAddresses();

    Address findAddressById(Long id);
}
