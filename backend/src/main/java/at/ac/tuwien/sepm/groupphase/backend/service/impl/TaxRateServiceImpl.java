package at.ac.tuwien.sepm.groupphase.backend.service.impl;


import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaxRateRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TaxRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class TaxRateServiceImpl implements TaxRateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TaxRateRepository taxRateRepository;

    @Autowired
    public TaxRateServiceImpl(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    @Override
    public List<TaxRate> getAllTaxRates() {
        LOGGER.trace("getAllTaxRates()");
        return this.taxRateRepository.findAll();
    }

    public TaxRate findTaxRateById(Long taxRateId) {
        LOGGER.trace("findTaxRateById({})", taxRateId);
        return this.taxRateRepository.findById(taxRateId).orElseThrow(() -> new NotFoundException("Steuersatz konnte nicht gefunden werden"));
    }
}
