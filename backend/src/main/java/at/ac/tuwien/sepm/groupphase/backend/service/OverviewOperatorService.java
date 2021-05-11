package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.OverviewOperator;

import java.util.List;

public interface OverviewOperatorService {

    /**
     * Find all operators.
     *
     * @return list of al operator entries
     */
    List<OverviewOperator> getAll();

}
