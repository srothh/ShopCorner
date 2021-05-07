package at.ac.tuwien.sepm.groupphase.backend.repository;

import org.springframework.dao.DataAccessException;
import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    /**
     * get invoice by username
     *
     * @param user
     * @return Invoice from the DB or null if the user does not exist
     * @throws DataAccessException is thrown when something goes wrong with the DB operation
     */
    Invoice findByUser(String user);

}
