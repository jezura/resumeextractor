package jobportal.dao;

import jobportal.models.Work;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WorksRepository extends CrudRepository <Work, Integer>
{

    List<Work> findWorksByContractor_Id(int id);

    @Query(
            value = "select * from works where year (work_date) = ?1 and month (work_date) = ?2 ",
            nativeQuery = true)
    List<Work> findWorksByDate(int year, int month);
}