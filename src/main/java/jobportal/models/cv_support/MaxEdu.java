package jobportal.models.cv_support;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class MaxEdu {

    private String maxEduLvl;
    private String generalEduField;

    public MaxEdu() {
    }

    public MaxEdu(String maxEduLvl, String generalEduField) {
        this.maxEduLvl = maxEduLvl;
        this.generalEduField = generalEduField;
    }

    public String getMaxEduLvl() {
        return maxEduLvl;
    }

    public void setMaxEduLvl(String maxEduLvl) {
        this.maxEduLvl = maxEduLvl;
    }

    public String getGeneralEduField() {
        return generalEduField;
    }

    public void setGeneralEduField(String generalEduField) {
        this.generalEduField = generalEduField;
    }
}