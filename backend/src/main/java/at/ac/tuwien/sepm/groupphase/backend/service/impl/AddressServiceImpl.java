package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Adds a new address to the database.
     *
     * @param address The address entity to add to the database
     * @return The added entity
     * @throws RuntimeException upon errors with the database
     */
    @Override
    public Address addNewAddress(Address address) {
        LOGGER.trace("addNewAddress({})", address);
        return addressRepository.save(address);
    }

    /**
     * Returns all addresses from the database.
     *
     * @return A list containing all the address entities from the database
     * @throws NotFoundException when no addresses are found
     * @throws RuntimeException  upon errors with the database
     */
    @Override
    public List<Address> getAllAddresses() {
        LOGGER.trace("getAllAddresses()");
        return addressRepository.findAll();
    }
}
