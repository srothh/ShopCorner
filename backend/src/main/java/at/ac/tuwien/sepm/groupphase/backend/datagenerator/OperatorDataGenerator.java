package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateData")
@Component
public class OperatorDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OperatorRepository operatorRepository;

    public OperatorDataGenerator(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    @PostConstruct
    public void generateOperators() {
        if (operatorRepository.findAll().size() > 0) {
            LOGGER.debug("operators already generated");
        } else {
            Operator operator = new Operator(1L, "test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            operatorRepository.save(operator);
            Operator operator2 = new Operator(2L, "anothertest", "randomLogName", "pspspspps", "best@icloud.com", Permissions.employee);
            operatorRepository.save(operator2);
        }
    }
}