package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import org.springframework.data.jpa.domain.Specification;

public class OperatorSpecifications {

    private OperatorSpecifications(){}

    public static Specification<Operator> hasPermission(Permissions permissions) {
        return (operator, cq, cb) -> cb.equal(operator.get("permissions"), permissions);
    }
}
