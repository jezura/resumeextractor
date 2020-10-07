package jobportal.dao;


import jobportal.models.OfferLanguage;
import jobportal.models.OfferSkill;
import org.springframework.data.repository.CrudRepository;

public interface OfferLanguageRepository extends CrudRepository <OfferLanguage, Integer>
{
    OfferLanguage findOfferLanguageById(String id);
}