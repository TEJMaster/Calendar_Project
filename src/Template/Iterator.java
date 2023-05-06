package Template;

/**
 * An interface for Iterator, contains two abstract methods and the first return a boolean hasNext(),
 * the second return an Object, which will be the next object in the Iterable Object we go through
 */
public interface Iterator {
    boolean hasNext();
    Object next();
}
