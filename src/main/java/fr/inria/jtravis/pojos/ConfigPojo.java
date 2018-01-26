package fr.inria.jtravis.pojos;

/**
 * An object representing config attribute both for {@link BuildPojo} and {@link JobPojo}
 *
 * @author Simon Urli
 */
public class ConfigPojo {
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigPojo that = (ConfigPojo) o;

        return language != null ? language.equals(that.language) : that.language == null;
    }

    @Override
    public int hashCode() {
        return language != null ? language.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ConfigPojo{" +
                "language='" + language + '\'' +
                '}';
    }
}
