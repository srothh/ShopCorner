package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class OperatorDataGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    public OperatorDataGenerator(OperatorRepository operatorRepository, EncoderConfig encoderConfig) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = encoderConfig.passwordEncoder();
    }

    @PostConstruct
    public void generateOperators() {
        if (operatorRepository.findAll().size() > 0) {
            LOGGER.debug("operators already generated");
        } else {
            Operator operator = new Operator("test", "logTest", "testpassw", "test@gmail.com", Permissions.admin);
            String password = passwordEncoder.encode(operator.getPassword());
            operator.setPassword(password);
            operatorRepository.save(operator);
            Operator operator2 = new Operator("anothertest", "randomLogName", "pspspspps", "best@icloud.com", Permissions.employee);
            String password2 = passwordEncoder.encode(operator.getPassword());
            operator2.setPassword(password);
            operatorRepository.save(operator2);
            Operator operator3 = new Operator("a third one", "Bonus Eventus", passwordEncoder.encode("Password"), "NoAdminAccountus@gmail.com", Permissions.employee);
            operatorRepository.save(operator3);
            operatorRepository.save(new Operator("gre", "Bonus Everegntus", passwordEncoder.encode("Password"), "AdminfAccountssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("graaa", "Bonegntus", passwordEncoder.encode("Password"), "sdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("gsade", "eregntus", passwordEncoder.encode("Password"), "as@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("ere", "Bondsf", passwordEncoder.encode("Password"), "gdsgs@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("grdsfe", "213 Everegntus", passwordEncoder.encode("Password"), "dsf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("fsasdd", "Bonsus 2312", passwordEncoder.encode("Password"), "dfffsdff@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("graasdasdaa", "54t4r", passwordEncoder.encode("Password"), "sasdasfddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("gdfasfsade", "fwe", passwordEncoder.encode("Password"), "aasdadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("dsfq", "Bfsdondsf", passwordEncoder.encode("Password"), "fdsfsfsdfdsff@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("safd", "aaaaa Efsdveregntus", passwordEncoder.encode("Password"), "AdminAccoufffntssaus@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("assf", "dg", passwordEncoder.encode("Password"), "sdsdsdsdf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("dfe", "eregfdsfsntus", passwordEncoder.encode("Password"), "asdss@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("dude", "ikikik", passwordEncoder.encode("Password"), "gdsgsds@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("saa", "213 opl", passwordEncoder.encode("Password"), "dssdaaaf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("kilil", "gggggaaaaa", passwordEncoder.encode("Password"), "sasddssdasddf@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("olaf", "scholz", passwordEncoder.encode("Password"), "aasdssssaadv@gmail.com", Permissions.admin));
            operatorRepository.save(new Operator("puioiuoui", "peepee", passwordEncoder.encode("Password"), "asadfdfads@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("Sandro", "Sandman", passwordEncoder.encode("Password"), "sandman@gmail.com", Permissions.employee));
            operatorRepository.save(new Operator("Carlos", "Carlos maximus", passwordEncoder.encode("Password"), "carlos@gmail.com", Permissions.employee));
        }
    }
}