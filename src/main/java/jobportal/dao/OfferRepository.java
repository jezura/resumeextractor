package jobportal.dao;


import jobportal.models.Offer;
import jobportal.models.Village;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository <Offer, Long>
{
    Offer findOfferById(Long id);
}