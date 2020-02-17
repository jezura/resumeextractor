package jobportal.services;

import jobportal.dao.PlanRepository;
import jobportal.models.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public Collection<Plan> findAllPlans(){
        List<Plan> plans = new ArrayList<Plan>();
        for (Plan plan :planRepository.findAll())
        {
            plans.add(plan);
        }
        return plans;
    }

    public void deletePlan(int id){
        planRepository.deleteById(id);
    }

    public void savePlan(Plan p){
        planRepository.save(p);
    }

    public Plan getPlanById(int id){
        return planRepository.findById(id).get();
    }

    public Plan findPlanForContractorId(int id){
        return planRepository.findPlanByContractor_id(id);
    }
    public void deleteContractorsPlan(int id ){
        if(findPlanForContractorId(id) != null) {
            int planId = findPlanForContractorId(id).getId();
            planRepository.deleteById(planId);
        }
    }

}