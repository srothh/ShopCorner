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
            Operator operator = new Operator(-1L, "test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            operatorRepository.save(operator);
            Operator operator2 = new Operator(-2L, "anothertest", "randomLogName", "pspspspps", "best@icloud.com", Permissions.employee);
            operatorRepository.save(operator2);
            Operator operator3 = new Operator(-3L, "a third one", "Bonus Eventus", "234n3knrk", "AdminAccountus@gmail.com", Permissions.employee);
            operatorRepository.save(operator3);
            operatorRepository.save(new Operator(-4L, "gre", "Bonus Everegntus", "234n3knrk", "AdminAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-5L, "fsd", "Bonsus Efsdveregntus", "234sdfn3knrk", "AdminAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-6L, "graaa", "Bonegntus", "234nsdf3knrk", "sdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-7L, "gsade", "eregntus", "234n3sknrk", "as@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator(-8L, "ere", "Bondsf", "234n3aknrk", "gdsgs@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-9L, "grdsfe", "213 Everegntus", "121212", "dsf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-10L, "fsasdd", "Bonsus 2312", "dsf", "dfffsdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-11L, "graasdasdaa", "54t4r", "sd", "sasdasddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-12L, "gdfasfsade", "fwe", "234n3saasdsknrk", "aasdadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator(-13L, "dsfq", "Bfsdondsf", "asd3d", "fdsfsfsdfdsff@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-14L, "gdfre", "ds Everegntus", "234n3knrk", "AdminAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-15L, "safd", "aaaaa Efsdveregntus", "234sssssdfn3knrk", "AdminAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-16L, "assf", "dg", "234nsdf3kdfsnrk", "sdsdsdsdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-17L, "dfe", "eregfdsfsntus", "234ndsf3sknrk", "asdss@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator(-18L, "dude", "ikikik", "234ndsd3aknrk", "gdsgsds@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-19L, "saa", "213 opl", "121fdsf212", "dssdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-20L, "heeer", "ds 2312", "dsfsff", "dfffssddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-21L, "kilil", "gggggaaaaa", "sddsdsd", "sasddssdasddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator(-22L, "olaf", "scholz", "sf", "aasdsaadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator(-23L, "puioiuoui", "peepee", "asd3sdd", "asadads@gmail.com", Permissions.employee));
        }
    }
}