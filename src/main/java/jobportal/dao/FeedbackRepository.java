package jobportal.dao;

import jobportal.models.Feedback;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FeedbackRepository extends CrudRepository <Feedback, Integer>
{
    List<Feedback> findFeedbackByDomain_Id(int id);
}