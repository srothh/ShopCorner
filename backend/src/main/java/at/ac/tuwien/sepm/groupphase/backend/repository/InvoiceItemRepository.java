package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    /**
     * Find all message entries ordered by published at date (descending).
     *
     * @return ordered list of al message entries
     * @throws RuntimeException upon encountering errors with the database
     */
    List<InvoiceItem> findAll();
}
