package jobportal.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OfferSkill {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "offer_id")
    private Long offerId;

    @NotNull
    @Column(name = "skill_id")
    private String skillId;

    @Column(name = "description")
    private String description;

    public OfferSkill() {}

    public OfferSkill(int id, @NotNull Long offerId, @NotNull String skillId, String description) {
        this.id = id;
        this.offerId = offerId;
        this.skillId = skillId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}