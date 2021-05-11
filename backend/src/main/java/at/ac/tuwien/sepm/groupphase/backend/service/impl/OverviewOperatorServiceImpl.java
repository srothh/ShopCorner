package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.OverviewOperator;
import at.ac.tuwien.sepm.groupphase.backend.service.OverviewOperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class OverviewOperatorServiceImpl implements OverviewOperatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //private final OperatorDao dao;

    @Override
    public List<OverviewOperator> getAll() {
        //return dao.getAll();
        return null;
    }
}
