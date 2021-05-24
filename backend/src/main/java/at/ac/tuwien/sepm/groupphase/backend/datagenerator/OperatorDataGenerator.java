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
            Operator operator = new Operator("test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            operatorRepository.save(operator);
            Operator operator2 = new Operator("anothertest", "randomLogName", "pspspspps", "best@icloud.com", Permissions.employee);
            operatorRepository.save(operator2);
            Operator operator3 = new Operator("a third one", "Bonus Eventus", "234n3knrk", "NoAdminAccountus@gmail.com", Permissions.employee);
            operatorRepository.save(operator3);
            operatorRepository.save(new Operator("gre", "Bonus Everegntus", "234n3knrk", "AdminfAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("graaa", "Bonegntus", "234nsdf3knrk", "sdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("gsade", "eregntus", "234n3sknrk", "as@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("ere", "Bondsf", "234n3aknrk", "gdsgs@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("grdsfe", "213 Everegntus", "121212", "dsf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("fsasdd", "Bonsus 2312", "dsf", "dfffsdff@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("graasdasdaa", "54t4r", "sd", "sasdasfddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("gdfasfsade", "fwe", "234n3saasdsknrk", "aasdadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("dsfq", "Bfsdondsf", "asd3d", "fdsfsfsdfdsff@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("safd", "aaaaa Efsdveregntus", "234sssssdfn3knrk", "AdminAccoufffntssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("assf", "dg", "234nsdf3kdfsnrk", "sdsdsdsdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("dfe", "eregfdsfsntus", "234ndsf3sknrk", "asdss@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("dude", "ikikik", "234ndsd3aknrk", "gdsgsds@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("saa", "213 opl", "121fdsf212", "dssdaaaf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("kilil", "gggggaaaaa", "sddsdsd", "sasddssdasddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("olaf", "scholz", "sf", "aasdssssaadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("puioiuoui", "peepee", "asd3sdd", "asadfdfads@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("Sandro", "Sandman", "Sandman", "sandman@gmail.com", Permissions.employee));
        }
    }
}