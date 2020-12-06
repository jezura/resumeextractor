package resumeextractor.models.cv_support;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class EduLog {
    private String logText = "";

    public EduLog() {
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public void addLogText(String logLine) {
        this.logText = this.logText.concat("\n" + logLine);
    }
}