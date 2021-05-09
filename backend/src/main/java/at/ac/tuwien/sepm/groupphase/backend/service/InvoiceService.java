package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    /**
     * Find a single message entry by id.
     *
     * @param id the id of the message entry
     * @return the message entry
     */
    Invoice findOne(Long id);


    /**
     * Find all invoice entries ordered by published at date (descending).
     *
     * @return ordered list of al message entries
     */
    List<Invoice> findAll();

}
