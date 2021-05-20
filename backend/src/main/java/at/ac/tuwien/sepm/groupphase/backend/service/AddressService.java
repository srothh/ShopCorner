package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;

/**  A service class handling addresses.
 */
public interface AddressService {
    /**
     * Adds a new address to the database.
     *
     * @param address The address entity to add to the database
     * @return The added entity
     * @throws RuntimeException upon encountering errors with the database
     */
    Address addNewAddress(Address address);

    /**
     * Returns all addresses from the database.
     *
     * @return A list containing all the address entities from the database
     * @throws RuntimeException  upon encountering errors with the database
     */
    List<Address> getAllAddresses();

    /**
     * Returns an address based on the id.
     *
     * @param id the id of the address
     * @return An address with the given id
     * @throws NotFoundException when no addresse could be found
     * @throws RuntimeException  upon encountering errors with the database
     */
    Address findAddressById(Long id);
}
