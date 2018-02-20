package fr.inria.jtravis.helpers;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.entities.Pagination;
import fr.inria.jtravis.entities.RepresentationType;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * This abstract helper is the base helper for all the others.
 * It defines constants for Travis CI API and methods to do requests and to parse them.
 *
 * @author Simon Urli
 */
public class EntityHelper extends AbstractHelper {
    public EntityHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public <T extends Entity> Optional<T> getEntityFromUri(Class<T> zeClass, String... uriComponent) {
        String url = this.buildUrl(uriComponent);
        try {
            String jsonContent = this.get(url);
            JsonObject jsonObj = getJsonFromStringContent(jsonContent);
            if (jsonObj != null) {
                return Optional.of(createGson().fromJson(jsonObj, zeClass));
            }
        } catch (IOException e) {
            this.getLogger().error("Error while getting JSON at URL: "+url);
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
