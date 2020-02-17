package jobportal.dao;


import jobportal.models.Plan;
import org.springframework.data.repository.CrudRepository;


public interface PlanRepository extends CrudRepository<Plan, Integer> {

    Plan findPlanByContractor_id(int id);
}
