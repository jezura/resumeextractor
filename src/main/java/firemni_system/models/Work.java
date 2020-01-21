package firemni_system.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "works")
public class Work extends BaseEntity {

    @ManyToOne
    @NotNull(message = "Choose work type")
    @JoinColumn(name = "work_type_id")
    private WorkType workType;

    @NotNull
    @Min(value = 1, message = "You must work at least 1 hour")
    @Max(value = 16, message = "I am pretty sure you haven't work for more than 16 hours")
    @Column(name = "hours_worked")
    private float hoursWorked;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @Column(name = "work_date")
    @NotNull(message = "Set date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Work() {
    }

    public Work(   @NotNull WorkType workType, @NotNull  float hoursWorked, String info,     @NotNull Contractor contractor, Domain domain,     @NotNull LocalDate workDate, Team team) {
        this.workType = workType;
        this.hoursWorked = hoursWorked;
        this.info = info;
        this.contractor = contractor;
        this.domain = domain;
        this.workDate = workDate;
        this.team = team;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public float getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(float hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
