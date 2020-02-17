package jobportal.services;

import jobportal.dao.WorksRepository;
import jobportal.models.Contractor;
import jobportal.models.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorksService {
    @Autowired
    private WorksRepository worksRepository;

    @Autowired
    private PersonService personService;


    public Collection<Work> findAllWorks(){
        List<Work> works = new ArrayList<Work>();
        worksRepository.findAll();
        for (Work work :worksRepository.findAll())
        {
            works.add(work);
        }
        return works;
    }
    public Collection<Work> sortWorksByDate( Collection<Work> worksToSort){
        List works = new ArrayList(worksToSort);
        Collections.sort(works, new Comparator<Work>() {
            public int compare(Work d1, Work d2) {
                return d2.getWorkDate().compareTo(d1.getWorkDate());
            }
        });
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

    public Collection<Work> filtrateWorksByYearAndMonth(int year, int month){

        List<Work> works = new ArrayList<Work>();
        for (Work work :worksRepository.findWorksByDate(year, month))
        {
            works.add(work);
        }
        return works;
    }
    public double hoursByYearAndMonth(Collection<Work> filteredWorks, int year, int month){

        double hoursMonthly = 0;
        for (Work work :filteredWorks)
        {
            if(work.getWorkDate().getYear() == year && work.getWorkDate().getMonthValue() == month)
            hoursMonthly = work.getHoursWorked() + hoursMonthly;
        }
        return hoursMonthly;
    }
    public Collection<Work> filterByUserID(int id, Collection<Work> worksByDate){
        List<Work> works = new ArrayList<Work>();
        for (Work work :worksByDate)
        {
            if(work.getContractor().getId() == id)
            works.add(work);
        }
        return works;
    }

    public Collection<Work> filterByMentorId(int id, Collection<Work> worksByDate){
        Collection<Contractor>contractors = personService.findContractorsByMentor(id);
        List<Work> works = new ArrayList<Work>();
        for (Work work :worksByDate)
        {
     for(Contractor contractor: contractors){
         if(work.getContractor().getId() == contractor.getId())
             works.add(work);
     }
        }
        return works;
    }


    public double hoursSum(Collection<Work> works){
        double hoursMonthly = 0;
            for (Work work :works)
            {
                hoursMonthly = work.getHoursWorked() + hoursMonthly;
            }
        return hoursMonthly;
    }

    public void deleteAllContractorsWorks(int id ){

        for (Work work :worksRepository.findWorksByContractor_Id(id))
        {
            deleteWork(work.getId());
        }
    }



}