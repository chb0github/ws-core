package org.bongiorno.common.utils.functions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * A function for collapsing many values into one.
 *
 * All three methods on this class have default implementations that are defined in terms of each other.  Subclasses
 * must provide an implementation for at least one of them which doesn't refer to the others.
 *
 * @param <T> The type of the things being combined or collapsed
 */
public abstract class FoldFunction<T> {

    /**
     * Reduce many things into one.  The default implementation does this by calling `fold' with null and the first
     * element, then calls `fold' with the result and the second element, continuing until all the elements have been
     * folded into the result.
     *
     * @param collection The things to be folded
     * @return One item that represents the group
     */
    public T fold(@Nullable Iterable<T> collection){
        T result = null;
        if(collection != null){
            for(T next : collection){
                result = fold(result, next);
            }
        }
        return result;
    }

    /**
     * Folds two items into one.  If one of the items is null, the default implementation will return the other one.
     * Otherwise, foldNotNull is called.
     */
    public T fold(@Nullable T first, @Nullable T second){
        return first == null ? second : second == null ? first : foldNotNull(first, second);
    }

    /**
     * Folds two non-null items into one.
     */
    protected T foldNotNull(@Nonnull T first, @Nonnull T second){
        return fold(Arrays.asList(first, second));
    }
}
