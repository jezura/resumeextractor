package jobportal.services;

import jobportal.dao.OfferSkillRepository;
import jobportal.dao.SkillRepository;
import jobportal.models.OfferSkill;
import jobportal.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OfferSkillService {
    @Autowired
    private OfferSkillRepository offerSkillRepository;

    public Collection<OfferSkill> findAllOfferSkills(){
        List<OfferSkill> offerSkills = new ArrayList<OfferSkill>();
        for (OfferSkill offerSkill:offerSkillRepository.findAll())
        {
            offerSkills.add(offerSkill);
        }
        return offerSkills;
    }

    public OfferSkill findOfferSkillById(String id) {
        return offerSkillRepository.findOfferSkillById(id);
    }

    public void saveOfferSkill(OfferSkill os){
        offerSkillRepository.save(os);
    }
}