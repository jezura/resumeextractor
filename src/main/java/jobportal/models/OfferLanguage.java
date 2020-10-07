package jobportal.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OfferLanguage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "offer_id")
    private Long offerId;

    @NotNull
    @Column(name = "language_id")
    private String languageId;

    @Column(name = "level")
    private String level;

    @Column(name = "description")
    private String description;

    public OfferLanguage() {}

    public OfferLanguage(int id, @NotNull Long offerId, @NotNull String languageId, String level, String description) {
        this.id = id;
        this.offerId = offerId;
        this.languageId = languageId;
        this.level = level;
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

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}