package at.ac.tuwien.sepm.groupphase.backend.service.impl;


import at.ac.tuwien.sepm.groupphase.backend.config.EncoderConfig;
import at.ac.tuwien.sepm.groupphase.backend.entity.Operator;
import at.ac.tuwien.sepm.groupphase.backend.entity.Permissions;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OperatorRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OperatorService;
import at.ac.tuwien.sepm.groupphase.backend.util.OperatorSpecifications;
import at.ac.tuwien.sepm.groupphase.backend.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import org.springframework.transaction.annotation.Transactional;

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
        throw new NotFoundException(String.format("Could not find the operator with the login name %s", loginName));
    }

    @Override
    public Operator findOperatorByEmail(String email) {
        LOGGER.trace("findOperatorByEmail({})", email);
        Operator operator = operatorRepository.findByEmail(email);
        if (operator != null) {
            return operator;
        }
        throw new NotFoundException(String.format("Could not find the operator with the email %s", email));
    }

    @Override
    @Cacheable(value = "operatorPages")
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

    @Cacheable(value = "counts", key = "'admins'")
    public Long getAdminCount() {
        LOGGER.trace("getAdminCount()");
        return operatorRepository.count(OperatorSpecifications.hasPermission(Permissions.admin));
    }

    @Cacheable(value = "counts", key = "'employees'")
    public Long getEmployeeCount() {
        LOGGER.trace("getEmployeeCount()");
        return operatorRepository.count(OperatorSpecifications.hasPermission(Permissions.employee));
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'admins'"),
        @CacheEvict(value = "counts", key = "'employees'"),
        @CacheEvict(value = "operatorPages", allEntries = true)
    })
    @Override
    public Operator save(Operator operator) {
        LOGGER.trace("save({})", operator);
        validator.validateNewOperator(operator, this);
        String password = passwordEncoder.encode(operator.getPassword());
        operator.setPassword(password);
        return operatorRepository.save(operator);
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'admins'"),
        @CacheEvict(value = "counts", key = "'employees'"),
        @CacheEvict(value = "operatorPages", allEntries = true)
    })
    public void changePermissions(Long id, Permissions permissions) {
        LOGGER.trace("changePermissions({})", id);
        operatorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Could not find operator that should get new permissions!"));
        operatorRepository.setOperatorPermissionsById(permissions, id);
    }


    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'admins'"),
        @CacheEvict(value = "counts", key = "'employees'"),
        @CacheEvict(value = "operatorPages", allEntries = true)
    })
    @Override
    public void delete(Long id) {
        LOGGER.trace("delete({})", id);
        operatorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Could not find operator that should be deleted!"));
        operatorRepository.deleteById(id);
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'admins'"),
        @CacheEvict(value = "counts", key = "'employees'"),
        @CacheEvict(value = "operatorPages", allEntries = true)
    })
    @Override
    public Operator update(Operator operator) {
        LOGGER.trace("update({})", operator);

        validator.validateUpdatedOperator(operator, this);

        Operator op = operatorRepository.findById(operator.getId())
            .orElseThrow(() -> new NotFoundException(String.format("Could not find the operator with the id %d", operator.getId())));

        op.setName(operator.getName());
        op.setLoginName(operator.getLoginName());
        op.setEmail(operator.getEmail());

        return operatorRepository.save(op);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        LOGGER.trace("updatePassword({})", id);
        Operator op = operatorRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Could not find the operator with the id %d", id)));

        if (passwordEncoder.matches(oldPassword, op.getPassword())) {
            op.setPassword(passwordEncoder.encode(newPassword));
            operatorRepository.save(op);
        } else {
            throw new ValidationException("Password could not be updated");
        }
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'admins'"),
        @CacheEvict(value = "counts", key = "'employees'"),
        @CacheEvict(value = "operatorPages", allEntries = true)
    })
    public void deleteAll() {
        operatorRepository.deleteAll();
    }

}
