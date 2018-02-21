package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

public class Warning {
    @Expose
    private String message;

    @Expose
    private String warningType;

    @Expose
    private String parameter;

    public String getMessage() {
        return message;
    }

    public String getWarningType() {
        return warningType;
    }

    public String getParameter() {
        return parameter;
    }

    protected Warning setMessage(String message) {
        this.message = message;
        return this;
    }

    protected Warning setWarningType(String warningType) {
        this.warningType = warningType;
        return this;
    }

    protected Warning setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public String toString() {
        String result = this.message;

        if (warningType != null) {
            result += " (type: " + warningType + ")";
        }
        if (parameter != null) {
            result += " (parameter: " + parameter + ")";
        }

        return result;
    }
}
