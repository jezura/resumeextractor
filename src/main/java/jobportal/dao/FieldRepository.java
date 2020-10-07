package jobportal.dao;


import jobportal.models.Field;
import jobportal.models.Language;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository <Field, Integer>
{
    Field findFieldById(String id);
}