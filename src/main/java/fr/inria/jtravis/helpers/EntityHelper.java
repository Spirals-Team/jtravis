package fr.inria.jtravis.helpers;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.entities.Pagination;
import fr.inria.jtravis.entities.RepresentationType;
import fr.inria.jtravis.entities.Warning;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * This abstract helper is the base helper for all the others.
 * It defines constants for Travis CI API and methods to do requests and to parse them.
 *
 * @author Simon Urli
 */
public class EntityHelper extends AbstractHelper {
    private JTravis jTravis;

    public EntityHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(config, client);
        this.jTravis = jTravis;
    }

    public <T extends Entity> Optional<T> getEntityFromUri(Class<T> zeClass, String uri) {
        return this.getEntityFromUri(zeClass, Collections.singletonList(uri), null);
    }

    public <T extends Entity> Optional<T> getEntityFromUri(Class<T> zeClass, List<String> pathComponent, Properties queryParameters) {
        String url = this.buildUrl(pathComponent, queryParameters);
        try {
            String jsonContent = this.get(url);
            JsonObject jsonObj = getJsonFromStringContent(jsonContent);
            if (jsonObj != null) {
                T result = createGson().fromJson(jsonObj, zeClass);
                try {
                    Field jTravisField = Entity.class.getDeclaredField("jtravis");
                    jTravisField.setAccessible(true);
                    jTravisField.set(result, this.jTravis);
                    jTravisField.setAccessible(false);
                } catch (NoSuchFieldException|IllegalAccessException e) {
                    this.getLogger().error("Error while setting jtravis field", e);
                }
                for (Warning warning : result.getWarnings()) {
                    getLogger().warn(warning.toString());
                }
                return Optional.of(result);
            }
        } catch (IOException e) {
            this.getLogger().error("Error while getting JSON at URL: "+url, e);
        }

        return Optional.empty();
    }

    public <T extends Entity> boolean refresh(T entity) {
        if (entity.getRepresentation() == RepresentationType.MINIMAL && entity.getUri() != null) {
            Optional<T> instance1Opt = (Optional<T>) this.getEntityFromUri(entity.getClass(), entity.getUri());

            if (instance1Opt.isPresent()) {
                T instance1 = instance1Opt.get();
                for (Field field : entity.getClass().getDeclaredFields()) {
                    Expose[] annotations = field.getAnnotationsByType(Expose.class);
                    if (annotations != null && annotations.length >= 1) {
                        try {
                            field.setAccessible(true);
                            Object value = field.get(instance1);
                            field.set(entity, value);
                            field.setAccessible(false);
                        } catch (IllegalAccessException e) {
                            this.getLogger().error("Error while setting field: "+field.getName(), e);
                            return false;
                        }
                    }
                }
                try {
                    Field representationField = Entity.class.getDeclaredField("representation");
                    if (representationField != null) {
                        representationField.setAccessible(true);
                        representationField.set(entity, RepresentationType.STANDARD);
                        representationField.setAccessible(false);
                    }
                } catch (NoSuchFieldException|IllegalAccessException e) {
                    this.getLogger().error("Error while setting representation field: "+entity.getClass().getName(), e);
                    return false;
                }

                return true;
            }
        }
        return false;
    }

    public <T extends EntityCollection> Optional<T> getNextCollection(T entityCollection) {
        Pagination currentPagination = entityCollection.getPagination();
        if (currentPagination != null && currentPagination.getNext() != null) {
            String nextUri = entityCollection.getPagination().getNext().getUri();
            return (Optional<T>) this.getEntityFromUri(entityCollection.getClass(), nextUri);
        }

        return Optional.empty();
    }

}
