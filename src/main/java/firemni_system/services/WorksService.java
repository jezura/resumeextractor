package firemni_system.services;

import firemni_system.dao.DomainRepository;
import firemni_system.dao.WorksRepository;
import firemni_system.models.Domain;
import firemni_system.models.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorksService {
    @Autowired
    private WorksRepository worksRepository;

    public Collection<Work> findAllWorks(){
        List<Work> works = new ArrayList<Work>();
        for (Work work :worksRepository.findAll())
        {
            works.add(work);
        }
        return works;
    }

    public void deleteWork(int id){
        worksRepository.deleteById(id);
    }

    public void saveWork(Work w){
        worksRepository.save(w);
    }

    public Work getWork(int id){
        return worksRepository.findById(id).get();
    }

    public Collection<Work> findWorksForContractor(int id){

        List<Work> works = new ArrayList<Work>();
        for (Work work :worksRepository.findWorksByContractor_Id(id))
        {
            works.add(work);
        }
return works;
    }
    public double hoursSum(Collection<Work> works){
        double hoursMonthly = 0;
        if (!works.isEmpty()){
            for (Work work :works)
            {
                hoursMonthly = work.getHoursWorked() + hoursMonthly;
            }
        }
        return hoursMonthly;
    }



}