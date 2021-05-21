package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.lang.invoke.MethodHandles;
import java.util.List;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void validateNewOperator(Operator operator, OperatorService operatorService) {
        LOGGER.trace("validateNewOperator({})", operator);

        List<Operator> operators = operatorService.findAll();
        for (Operator op : operators) {

            if (op.getEmail().equals(operator.getEmail())) {
                throw new ValidationException("Account already exists");
            }

            if (op.getLoginName().equals(operator.getLoginName())) {
                throw new ValidationException("Account already exists");
            }
        }
    }

    public void validateUpdatedOperator(Operator operator, OperatorService operatorService) {
        LOGGER.trace("validateUpdatedOperator({})", operator);

        List<Operator> operators = operatorService.findAll();
        for (Operator op : operators) {

            if (!op.getId().equals(operator.getId()) && op.getEmail().equals(operator.getEmail())) {
                throw new ValidationException("Account already exists");
            }

            if (!op.getId().equals(operator.getId()) && op.getLoginName().equals(operator.getLoginName())) {
                throw new ValidationException("Account already exists");
            }
        }
    }

}
