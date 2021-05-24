package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for operators.
 */
@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    /**
     * Finds an operator based on the login name.
     *
     * @return an operator with the login name
     */
    Operator findByLoginName(String loginName);
}
