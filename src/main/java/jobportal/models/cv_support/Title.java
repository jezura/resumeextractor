package jobportal.models.cv_support;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull(message = "Choose academic title")
    @Column(name = "title_variant")
    private String titleVariant;

    @NotNull(message = "Choose academic title")
    @Column(name = "official_version")
    private String officialVersion;

    @NotNull(message = "Choose academic degree")
    @Column(name = "degree")
    private String degree;

    @Column(name = "school_type")
    private String schoolType;

    public Title() {
    }

    public Title(int id, @NotNull(message = "Choose academic title") String titleVariant, @NotNull(message = "Choose academic title") String officialVersion, @NotNull(message = "Choose academic degree") String degree, String schoolType) {
        this.id = id;
        this.titleVariant = titleVariant;
        this.officialVersion = officialVersion;
        this.degree = degree;
        this.schoolType = schoolType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleVariant() {
        return titleVariant;
    }

    public void setTitleVariant(String titleVariant) {
        this.titleVariant = titleVariant;
    }

    public String getOfficialVersion() {
        return officialVersion;
    }

    public void setOfficialVersion(String officialVersion) {
        this.officialVersion = officialVersion;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }
}