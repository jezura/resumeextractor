package jobportal.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OfferBenefit {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "offer_id")
    private Long offerId;

    @NotNull
    @Column(name = "benefit_id")
    private String benefitId;

    @Column(name = "description")
    private String description;

    public OfferBenefit() {}

    public OfferBenefit(int id, @NotNull Long offerId, @NotNull String benefitId, String description) {
        this.id = id;
        this.offerId = offerId;
        this.benefitId = benefitId;
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

    public String getBenefitId() {
        return benefitId;
    }

    public void setBenefitId(String benefitId) {
        this.benefitId = benefitId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}