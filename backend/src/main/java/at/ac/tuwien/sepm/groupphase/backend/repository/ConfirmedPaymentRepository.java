package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.ConfirmedPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmedPaymentRepository extends JpaRepository<ConfirmedPayment, Long> {
    /**
     * Finds a ConfirmedPayment by it's payerId and paymentId.
     *
     * @param payerId the payerId to look for in a ConfirmedPayment
     * @param paymentId the paymentId to look for in a ConfirmedPayment
     * @return the ConfirmedPayment with the given paymentId and the payerId
     * @throws RuntimeException upon encountering errors with the database
     */
    @Query("select cp from ConfirmedPayment cp where cp.payerId =:payerId and cp.paymentId =:paymentId")
    ConfirmedPayment getConfirmedPaymentByPayerIdAndPaymentId(@Param("payerId")String payerId, @Param("paymentId")String paymentId);
}

