package firemni_system.dao;

import firemni_system.models.Feedback;
import firemni_system.models.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FeedbackRepository extends CrudRepository <Feedback, Integer>
{
    List<Feedback> findFeedbackByDomain_Id(int id);
}