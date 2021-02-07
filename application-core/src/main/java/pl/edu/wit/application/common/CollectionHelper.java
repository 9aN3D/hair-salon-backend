package pl.edu.wit.application.common;

import java.util.Collection;

import static java.util.Objects.isNull;

public class CollectionHelper {

    public static boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

}
