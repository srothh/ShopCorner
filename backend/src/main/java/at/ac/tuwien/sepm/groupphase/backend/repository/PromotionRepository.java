package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    /**
     * Finds a Promotion by its code.
     *
     * @param code the code to look for in a Promotion
     *
     * @return the Promotion with the given code
     * */
    @Query("select p from Promotion p where p.code =:code")
    Promotion getPromotionByCode(@Param("code") String code);

}
