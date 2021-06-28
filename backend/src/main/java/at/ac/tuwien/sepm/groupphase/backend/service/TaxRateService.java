package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;


import java.util.List;
/**
 * Service class handling business logic for tax-rates.
 * */

public interface TaxRateService {
    /**
     * Gets all tax_rates that were previously added in the database.
     *
     * @return all tax_rates that are currently saved in the database.
     * @throws RuntimeException upon encountering errors with the database
     */
    List<TaxRate> getAllTaxRates();

    /**
     * Gets a tax-rate specified by the id.
     *
     * @param taxRateId the id of the tax-rate to retrieve the associated entity
     * @return tax-rate with the given Id
     * @throws NotFoundException is thrown when the id could not be found in the database
     * @throws RuntimeException is thrown when an error occurred during data processing
     */
    TaxRate findTaxRateById(Long taxRateId);

}
