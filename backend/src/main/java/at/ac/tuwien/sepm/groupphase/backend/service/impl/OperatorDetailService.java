package at.ac.tuwien.sepm.groupphase.backend.service.impl;


import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class OperatorDetailService implements OperatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public OperatorDetailService(OperatorRepository operatorRepository, EncoderConfig encoderConfig, Validator validator) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = encoderConfig.passwordEncoder();
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    /**
     * Find all operators.
     *
     * @return a list of operators
     */
    @Override
    public List<Operator> findAll() {
        LOGGER.trace("findAll()");
        return operatorRepository.findAll();
    }


    /**
     * Saves the specified operator.
     *
     * @param operator to be saved
     * @return the operator that has just been saved.
     */
    @Override
    public Operator save(Operator operator) {
        LOGGER.trace("save({})", operator);
        validator.validateNewOperator(operator, this);
        String password = passwordEncoder.encode(operator.getPassword());
        operator.setPassword(password);
        return operatorRepository.save(operator);
    }

}
