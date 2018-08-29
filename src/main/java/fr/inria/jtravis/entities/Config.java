package fr.inria.jtravis.entities;

import java.util.Objects;

import com.google.gson.annotations.Expose;

public final class Config {

    @Expose
    private String language;

    public String getLanguage() {
        return language;
    }

    protected void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Config config = (Config) o;
        return Objects.equals(language, config.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language);
    }
}
