package org.kaccag.io;

import java.util.List;

/**
 * Interface for any object to be saved.
 * <p>
 * Intended that this will be extended for specific databases,
 * and objects for that database.
 *
 * @param <T>
 */
public abstract class Saveable<T> {

    public abstract T addOne(T obj);

    public abstract T updateOne(T update, T search);

    public abstract T delete(T search);

    public abstract T getOne(Object field);

    public abstract List<T> getMany(T search);

}
