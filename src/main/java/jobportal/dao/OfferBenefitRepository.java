package jobportal.dao;


import jobportal.models.OfferBenefit;
import jobportal.models.OfferSkill;
import org.springframework.data.repository.CrudRepository;

public interface OfferBenefitRepository extends CrudRepository <OfferBenefit, Integer>
{
    OfferBenefit findOfferBenefitById(String id);
}