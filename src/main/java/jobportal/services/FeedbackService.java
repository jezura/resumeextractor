package jobportal.services;

import jobportal.dao.DomainRepository;
import jobportal.dao.FeedbackRepository;
import jobportal.models.Domain;
import jobportal.models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private DomainRepository domainRepository;

    public Collection<Feedback> findAllFeedbacks(){
        List<Feedback> feedbacks = new ArrayList<Feedback>();
        for (Feedback feedback :feedbackRepository.findAll())
        {
            feedbacks.add(feedback);
        }
        return feedbacks;
    }

    public void saveFeedback(Feedback f){
        feedbackRepository.save(f);
    }

    public Feedback getFeedback(int id){
        return feedbackRepository.findById(id).get();
    }

    public Collection<Feedback> findFeedbackForDomain(int id){
        return feedbackRepository.findFeedbackByDomain_Id(id);
    }

    public Collection<Feedback> findFeedbacksForContractor(int id){

        List<Domain> domains = new ArrayList<Domain>();
        List<Feedback> feedbacks = new ArrayList<Feedback>();
        for (Domain domain : domainRepository.findDomainsByContractor_id(id))
        {
            for (Feedback feedback : feedbackRepository.findFeedbackByDomain_Id(domain.getId()))
            {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public Collection<Feedback> findFeedbacksForValidator(int id){

        List<Domain> domains = new ArrayList<Domain>();
        List<Feedback> feedbacks = new ArrayList<Feedback>();
        for (Domain domain : domainRepository.findDomainsByValidator_id(id))
        {
            for (Feedback feedback : feedbackRepository.findFeedbackByDomain_Id(domain.getId()))
            {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }
}