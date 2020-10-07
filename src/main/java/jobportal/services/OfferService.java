package jobportal.services;

import jobportal.dao.OfferRepository;
import jobportal.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public Collection<Offer> findAllOffers(){
        List<Offer> offers = new ArrayList<Offer>();
        for (Offer offer :offerRepository.findAll())
        {
            offers.add(offer);
        }
        return offers;
    }

    public Offer findOfferById(Long id) {
        return offerRepository.findOfferById(id);
    }

    public void saveOffer(Offer o){
        offerRepository.save(o);
    }
}