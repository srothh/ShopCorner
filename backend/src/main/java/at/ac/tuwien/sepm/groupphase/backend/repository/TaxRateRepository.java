package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
    @Query("SELECT DISTINCT t FROM TaxRate t LEFT JOIN FETCH t.products")
    public List<TaxRate> getAllTaxRates();
}
