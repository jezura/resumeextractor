package firemni_system.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Trida objektu Contractor
 */

@Entity
@Table(name = "contractors")
public class Contractor extends Person {

    @Column(name = "hire_date")
    @NotNull(message = "Set hire date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @Column(name = "address")
    @NotEmpty(message = "Set contractors address")
    private String address;

    @Column(name = "city")
    @NotEmpty(message = "Set contractors city")
    private String city;

    @Column(name = "telephone")
    @NotEmpty
    @Min(value = 100000000, message = "Set real phone number, format: xxxxxxxxx")
    @Max(value = 999999999)
    private String telephone;

    @ManyToOne
    @NotNull(message = "Choose validator")
    @JoinColumn(name = "validator_id")
    private Validator mentor;

    @ManyToOne
    @NotNull(message = "Choose team")
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @NotNull(message = "Choose swimlane")
    @JoinColumn(name = "swimlane_id")
    private SwimlaneType swimlane;

    // constructors
    public Contractor() {
    }

    public Contractor(@NotNull LocalDate hireDate, @NotEmpty(message = "Set contractors address") String address, @NotEmpty(message = "Set contractors address") String city, @Min(value = 100000000, message = "Set real phone number") @Max(value = 999999999, message = "Set real phone number") String telephone, @NotNull(message = "Choose validator") Validator mentor, @NotNull(message = "Choose team") Team team, @NotNull(message = "Choose swimlane") SwimlaneType swimlane) {
        this.hireDate = hireDate;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.mentor = mentor;
        this.team = team;
        this.swimlane = swimlane;
    }

    @Transient
    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Validator getMentor() {
        return mentor;
    }

    public void setMentor(Validator mentor) {
        this.mentor = mentor;
    }

    public SwimlaneType getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(SwimlaneType swimlane) {
        this.swimlane = swimlane;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
