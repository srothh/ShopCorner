package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * Finds a Promotion by its code.
     *
     * @param code the code to look for in a Promotion
     * @return the Promotion with the given code
     * @throws RuntimeException upon encountering errors with the database
     */
    Promotion findByCode(String code);

}
