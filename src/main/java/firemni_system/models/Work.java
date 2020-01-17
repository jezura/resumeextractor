package firemni_system.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "works")
public class Work extends BaseEntity {

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "workType_id")
    private WorkType workType;

    @Column(name = "hoursWorked")
    @NotEmpty
    private double hoursWorked;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @OneToOne
    @NotEmpty
    @JoinColumn(name = "domain_id")
    private Domain domain;

    public Work() {
    }

    public Work(@NotEmpty WorkType workType, @NotEmpty double hoursWorked, String info, @NotEmpty Contractor contractor, Domain domain) {
        this.workType = workType;
        this.hoursWorked = hoursWorked;
        this.info = info;
        this.contractor = contractor;
        this.domain = domain;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
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
}
