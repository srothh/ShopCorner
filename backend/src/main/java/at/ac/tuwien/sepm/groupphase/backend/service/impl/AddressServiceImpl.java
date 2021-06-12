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

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address addNewAddress(Address address) {
        LOGGER.trace("addNewAddress({})", address);
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        LOGGER.trace("getAllAddresses()");
        return addressRepository.findAll();
    }

    public Address findAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find address!"));
    }
}
