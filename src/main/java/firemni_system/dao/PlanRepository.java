package firemni_system.dao;


import firemni_system.models.Contractor;
import firemni_system.models.Domain;
import firemni_system.models.Plan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PlanRepository extends CrudRepository<Plan, Integer> {

    Plan findPlanByContractor_id(int id);
}
