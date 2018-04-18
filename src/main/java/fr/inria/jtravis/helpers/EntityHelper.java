package fr.inria.jtravis.helpers;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import fr.inria.jtravis.Http404Exception;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This abstract helper is the base helper for all the others.
 * It defines constants for Travis CI API and methods to do requests and to parse them.
 *
 * @author Simon Urli
 */
public class EntityHelper extends AbstractHelper {

    public EntityHelper(JTravis jTravis) {
        super(jTravis);
    }

    public <T extends Entity> Optional<T> getEntityFromUri(Class<T> zeClass, String uri) {
        return this.getEntityFromUri(zeClass, Collections.singletonList(uri), null);
    }

    public <T extends Entity> Optional<T> getEntityFromUri(Class<T> zeClass, List<String> pathComponent, Properties queryParameters) {
        Optional<T> ret = Optional.empty();

        String url = this.buildUrl(pathComponent, queryParameters);
        try {
            String jsonContent = this.get(url);
            JsonObject jsonObj = getJsonFromStringContent(jsonContent);
            if (jsonObj != null) {
                T result = createGson().fromJson(jsonObj, zeClass);
                try {
                    Method jtravisSetter = Entity.class.getDeclaredMethod("setJtravis", JTravis.class);
                    jtravisSetter.setAccessible(true);
                    jtravisSetter.invoke(result, this.getjTravis());

                    for (Field field : zeClass.getDeclaredFields()) {
                        if (field.getAnnotation(Expose.class) != null) {
                            field.setAccessible(true);
                            if (Entity.class.isAssignableFrom(field.getType())) {
                                Entity fieldEntity = (Entity) field.get(result);
                                if (fieldEntity != null) {
                                    jtravisSetter.invoke(fieldEntity, this.getjTravis());
                                }
                            } else if (Iterable.class.isAssignableFrom(field.getType())) {
                                Iterable fieldCollection = (Iterable) field.get(result);

                                for (Object entity : fieldCollection) {
                                    if (entity instanceof Entity) {
                                        jtravisSetter.invoke(entity, this.getjTravis());
                                    }
                                }
                            }
                            field.setAccessible(false);
                        }
                    }
                    jtravisSetter.setAccessible(false);

                } catch (IllegalAccessException|NoSuchMethodException|InvocationTargetException e) {
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
        return ret;
    }

    public <T extends Entity> boolean refresh(T entity) {
        if (entity.getUri() != null) {
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

                if (entity.getRepresentation() == RepresentationType.MINIMAL) {
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
