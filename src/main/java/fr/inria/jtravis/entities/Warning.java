package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Warning warning = (Warning) o;
        return Objects.equals(message, warning.message) &&
                Objects.equals(warningType, warning.warningType) &&
                Objects.equals(parameter, warning.parameter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, warningType, parameter);
    }
}
