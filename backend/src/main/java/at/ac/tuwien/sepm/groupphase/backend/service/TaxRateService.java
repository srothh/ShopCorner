package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;

import java.util.List;
/**
 * Service class handling business logic for tax-rates.
 * */

public interface TaxRateService {
    /**
     * Gets all tax_rates that were previously added in the database.
     *
     *
     * @return all tax_rates that are currently saved in the database.
     *
     */
    List<TaxRate> getAllTaxRates();
}
