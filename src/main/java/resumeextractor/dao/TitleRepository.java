package resumeextractor.dao;


import resumeextractor.models.cv_support.Title;
import org.springframework.data.repository.CrudRepository;

public interface TitleRepository extends CrudRepository <Title, Integer>
{
    Title findTitleById(int id);
    Title findTitleByTitleVariant(String titleVariant);
}