package pl.edu.wit.hairsalon.sharedKernel;

import java.util.Collection;

import static java.util.Objects.isNull;

public class CollectionHelper {

    public static boolean nonNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static Collection<?> requireNotNullOrEmpty(Collection<?> collection, String message) {
        if (isNullOrEmpty(collection)) {
            throw new NullPointerException(message);
        }
        return collection;
    }

}
