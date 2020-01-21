package firemni_system.services;

import firemni_system.dao.SwimlaneRepository;
import firemni_system.dao.TeamsRepository;
import firemni_system.dao.WorksRepository;
import firemni_system.dao.WorktypeRepository;
import firemni_system.models.SwimlaneType;
import firemni_system.models.Team;
import firemni_system.models.Work;
import firemni_system.models.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CiselnikyService {
    @Autowired
    private WorktypeRepository worktypeRepository;

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private SwimlaneRepository swimlaneRepository;

    public Collection<WorkType> findAllWorkTypes(){
        List<WorkType> workTypes = new ArrayList<WorkType>();
        for (WorkType workType :worktypeRepository.findAll())
        {
            workTypes.add(workType);
        }
        return workTypes;
    }

    public Collection<Team> findAllTeams(){
        List<Team> teams = new ArrayList<Team>();
        for (Team team :teamsRepository.findAll())
        {
            teams.add(team);
        }
        return teams;
    }

    public Collection<SwimlaneType> findAllSwimlanes(){
        List<SwimlaneType> swimlanes = new ArrayList<SwimlaneType>();
        for (SwimlaneType swimlane :swimlaneRepository.findAll())
        {
            swimlanes.add(swimlane);
        }
        return swimlanes;
    }


}