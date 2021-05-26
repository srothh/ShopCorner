package at.ac.tuwien.sepm.groupphase.backend.service.impl;


import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import at.ac.tuwien.sepm.groupphase.backend.util.OperatorSpecifications;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public OperatorServiceImpl(OperatorRepository operatorRepository, EncoderConfig encoderConfig, Validator validator) {
        this.operatorRepository = operatorRepository;
        this.passwordEncoder = encoderConfig.passwordEncoder();
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        LOGGER.trace("loadOperatorByUsername({})", loginName);
        try {
            Operator operator = this.findOperatorByLoginName(loginName);
            String authority = "";
            switch (operator.getPermissions()) {
                case admin:
                    authority = "ROLE_ADMIN";
                    break;
                case employee:
                    authority = "ROLE_EMPLOYEE";
                    break;
                default:
                    break;
            }
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(authority);
            return new User(operator.getLoginName(), operator.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public Operator findOperatorByLoginName(String loginName) {
        LOGGER.trace("findOperatorByLoginName({})", loginName);
        Operator operator = operatorRepository.findByLoginName(loginName);
        if (operator != null) {
            return operator;
        }
        throw new NotFoundException(String.format("Could not find the customer with the login name %s", loginName));
    }

    @Override
    public Page<Operator> findAll(int page, int pageCount, Permissions permissions) {
        LOGGER.trace("findAll({})", page);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return operatorRepository.findAll(OperatorSpecifications.hasPermission(permissions), returnPage);
    }

    @Override
    public List<Operator> findAll() {
        LOGGER.trace("findAll()");
        return operatorRepository.findAll();
    }

    @Override
    public int[] getCollectionSize() {
        LOGGER.trace("getCollectionsize()");
        return new int[]{(int) operatorRepository.count(OperatorSpecifications.hasPermission(Permissions.admin)),
            (int) operatorRepository.count(OperatorSpecifications.hasPermission(Permissions.employee))};
    }

    @Override
    public Operator save(Operator operator) {
        LOGGER.trace("save({})", operator);
        validator.validateNewOperator(operator, this);
        String password = passwordEncoder.encode(operator.getPassword());
        operator.setPassword(password);
        return operatorRepository.save(operator);
    }

    @Override
    public void delete(Long id) {
        LOGGER.trace("delete({})", id);
        operatorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Could not find operator that should be deleted!"));
        operatorRepository.deleteById(id);
    }
}
