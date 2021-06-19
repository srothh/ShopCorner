package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * A repository class for operators.
 */
@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
    /**
     * Finds an operator based on the login name.
     *
     * @return an operator with the login name
     * @throws RuntimeException  upon encountering errors with the database
     */
    Operator findByLoginName(String loginName);

    /**
     * Finds an operator based on the email address.
     *
     * @return an operator with the given email
     * @throws RuntimeException  upon encountering errors with the database
     */
    Operator findByEmail(String email);

    /**
     * Changes Permissions of an operator with given id.
     *
     * @param permissions that should overwrite the old one
     * @param id of Operator that should be updated
     */
    @Modifying
    @Query("update Operator o set o.permissions = ?1 where o.id = ?2")
    void setOperatorPermissionsById(Permissions permissions, Long id);
}
