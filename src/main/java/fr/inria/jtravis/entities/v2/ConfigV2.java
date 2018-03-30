package fr.inria.jtravis.entities.v2;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public final class ConfigV2 {

    @Expose
    private String language;

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ConfigV2 configV2 = (ConfigV2) o;
        return Objects.equals(language, configV2.language);
    }

    @Override
    public int hashCode() {

        return Objects.hash(language);
    }
}
