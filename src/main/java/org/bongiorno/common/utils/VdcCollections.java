package org.bongiorno.common.utils;

import org.bongiorno.common.utils.functions.ConstantFunction;
import org.bongiorno.common.utils.functions.predicates.NotPredicate;
import org.bongiorno.common.utils.functions.ConstantFunction;
import org.bongiorno.common.utils.functions.predicates.NotPredicate;

import java.util.*;

/**
 * @author cbongiorno
 *         Date: 4/22/12
 *         Time: 2:19 PM
 */
public final class VdcCollections {


    public static <K, V> Map<K, V> exceptionOnDuplicateMap(Map<K, V> m) {
        return new ExceptionOnDuplicateKeyMap<K, V>(m);
    }

    public static <K, V> SortedMap<K, V> exceptionOnDuplicateSortedMap(SortedMap<K, V> m) {
        return new ExceptionOnDuplicateKeySortedMap<K, V>(m);
    }

    public static <T> Collection<T> delimitedCollection(Collection<T> c, String delimiter) {
        return new DelimitedCollection<T>(c, delimiter);
    }
    /**
     *
     * @param m the map to delegate to
     * @param postKeyDelim what you want to come after every key
     * @param postValueDelim what you want to come after every value
     * @param <K>
     * @param <V>
     * @return a map that behaves as 'm' except has a very nice toString()
     */
    public static <K, V> Map<K, V> delimitedMap(Map<K, V> m, CharSequence postKeyDelim, CharSequence postValueDelim) {
        return new DelimitedMap<K, V>(m,postKeyDelim,postValueDelim);
    }

    /**
     *
     * @param m the map to delegate to
     * @param preKey what you want to come before every key
     * @param postKey what you want to come after every key
     * @param postValue what you want to come after every value
     * @param <K>
     * @param <V>
     * @return a map that behaves as 'm' except has a very nice toString()
     */
    public static <K, V> Map<K, V> delimitedMap(Map<K, V> m, CharSequence preKey, CharSequence postKey, CharSequence postValue) {
        return new DelimitedMap<K, V>(m, preKey,postKey, postValue);
    }

    /**
     *
     * @param m the map to delegate to
     * @param preKey what you want to come before every key
     * @param postKey what you want to come after every key
     * @param postValue what you want to come after every value
     * @param trim if, when output is complete, you want the last postValue to be trimmed off
     * @param <K>
     * @param <V>
     * @return a map that behaves as 'm' except has a very nice toString()
     */
    public static <K, V> Map<K, V> delimitedMap(Map<K, V> m, CharSequence preKey, CharSequence postKey, CharSequence postValue, boolean trim) {
        return new DelimitedMap<K, V>(m, preKey,postKey, postValue, trim);
    }

    public static <T> Set<T> exceptionOnDuplicateSet(Set<T> s) {
        return new ExceptionOnDuplicateSet<T>(s);
    }

    /**
     * Wraps the given map in a new map whose <code>get()</code> operation returns the given value instead of
     * <code>null</code> when the key is not present in the map.  <code>get()</code> may still return <code>null</code>
     * if a null value was explicitly added to the map.
     *
     * @param delegate The map to be wrapped
     * @param defaultValue Constant value to be returned from get when the sought key is not present
     *
     * @return The wrapped map
     */
    public static <K,V> Map<K,V> defaultValueMap(Map<K,V> delegate, V defaultValue){
        return new DefaultValueMap<K, V>(delegate, new ConstantFunction<Object, V>(defaultValue));
    }

    /**
     * Wraps the given map in a new map whose <code>get()</code> operation returns the result of calling the given
     * Function on the key instead of <code>null</code> when the key is not present in the map.  <code>get()</code> may
     * still return <code>null</code> if a null value was explicitly added to the map.
     *
     * @param delegate The map to be wrapped
     * @param defaultValueFunction Function to be called to find the default value when a key is not present in the map
     *
     * @return the wrapped map
     */
    public static <K,V> Map<K,V> defaultValueMap(Map<K,V> delegate, Function<Object, V> defaultValueFunction){
        return new DefaultValueMap<K, V>(delegate, defaultValueFunction);
    }

    /**
     * transforms fromCollection to an output collection after applying 'function'. Attempts
     * to use the same Collection type as the one passed in
     * @param fromCollection duh
     * @param toCollection duh
     * @param function the function to apply to each of the elements fromCollection
     * @param <F> input to the function
     * @param <T> output to the function
     * @return a new Collection of the same type as fromCollection
     */
    public static <F, T, C extends Collection<T>> C transform(Collection<F> fromCollection,
                                                 C toCollection,
                                                 Function<? super F, T> function) {
        for (F f : fromCollection) {
            toCollection.add(function.apply(f));
        }
        return toCollection;

    }

    public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
        // because this returns a collection, we can do as we wish. I am starting to see the wisdom of Guava in this regard
        return transform(fromCollection,new HashSet<T>(),function);

    }

    public static <F, T> Set<T> transform(Set<F> fromSet, Function<? super F, T> function) {
        return transform(fromSet,new HashSet<T>(),function);
    }

    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, T> function) {
        return transform(fromList,new LinkedList<T>(),function);
    }

    public static <F, T> Collection<T> transformMulti(Collection<F> fromCollection, Function<? super F, ? extends Collection<T>> function){
        Collection<T> toCollection = new LinkedList<T>();
        if(fromCollection != null){
            for (F element : fromCollection) {
                toCollection.addAll(function.apply(element));
            }
        }
        return toCollection;
    }

    /**
     * Find an element that matches the predicate.  The returned element will be the first matching element by iteration
     * order.
     *
     * @param collection Items through which to search
     * @param predicate Function for identifying matching elements
     *
     * @return First matching element
     */
    public static <T> T findOne(Iterable<T> collection, Function<? super T, Boolean> predicate){
        T result = null;
        if(collection != null){
            for (T item : collection) {
                if(predicate.apply(item)){
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Return a new Collection returning all items in the specified Iterable that match predicate
     * @param collection Items through which to search
     * @param predicate Function for identifying matching elements
     *
     * @return All matching elements
     */
    public static <T> Collection<T> findAll(Iterable<T> collection, Function<? super T, Boolean> predicate){
        return findAll(collection, new ArrayList<T>(), predicate);
    }

    /**
     * Return a new Collection returning all items in the specified Iterable that match predicate
     * @param fromCollection Items through which to search
     * @param toCollection destination collection
     * @param predicate Function for identifying matching elements
     *
     * @return All matching elements
     */
    public static <T, C extends Collection<T>> C findAll(Iterable<T> fromCollection, C toCollection, Function<? super T, Boolean> predicate){
        if(fromCollection != null){
            for(T item : fromCollection){
                if(predicate.apply(item)){
                    toCollection.add(item);
                }
            }
        }
        return toCollection;
    }

    /**
     * Remove from the specified Iterable all elements that match the specified predicate
     *
     * @param collection Group to be purged
     * @param predicate Function to identify unwanted elements
     */
    public static <T> void removeIf(Iterable<T> collection, Function<? super T, Boolean> predicate){
        Iterator<T> i = collection.iterator();
        while(i.hasNext()){
            if(predicate.apply(i.next())){
                i.remove();
            }
        }
    }

    /**
     * Remove from the specified Iterable all elements that do not match the specified predicate
     *
     * @param collection Group to be purged
     * @param predicate Function to identify elements to be kept
     */
    public static <T> void removeIfNot(Iterable<T> collection, Function<? super T, Boolean> predicate){
        removeIf(collection, new NotPredicate<T>(predicate));
    }

    /**
     * Counts duplicate items, returning a map of the unique item to the number of times it appears.
     * @param list The items to be counted
     *
     * @return A map of item to number of appearances
     */
    public static <T> Map<T, Integer> countDuplicates(Iterable<T> list){
        Map<T, Integer> result = defaultValueMap(new HashMap<T, Integer>(), 0);
        if(list != null){
            for(T element : list){
                Integer count = result.get(element);
                result.put(element, count + 1);
            }
        }
        return result;
    }

    /**
     * Map equivalent of Arrays.asList.  This works just like the way you would declare a hash (map) in Perl, except I
     * can't make Java treat "=>" as an equivalent for ",".
     *
     * @param pairs Keys and values, alternately
     *
     * @throws ArrayIndexOutOfBoundsException If an odd number of Objects are passed in
     */
    public static <T> Map<T,T> asMap(T... pairs) throws ArrayIndexOutOfBoundsException{
        Map<T,T> retVal = new HashMap<T, T>();
        int i = 0;
        while(i < pairs.length){
            retVal.put(pairs[i++], pairs[i++]);
        }
        return retVal;
    }

    public static <K, V> Map<K, V> putInMap(Iterable<K> things, Function<? super K, V> valueFunction){
        return putInMap(things, new HashMap<K, V>(), valueFunction);
    }

    public static <K, V> Map<K, V> putInMap(Iterable<K> things, V value){
        return putInMap(things, new HashMap<K, V>(), value);
    }

    public static <C extends Map<K, V>, K, V> C putInMap(Iterable<K> things, C destinationMap, Function<? super K, V> valueFunction){
        for (K thing : things) {
            destinationMap.put(thing, valueFunction.apply(thing));
        }
        return destinationMap;
    }

    public static <C extends Map<K,V>, K, V> C putInMap(Iterable<K> things, C destinationMap, V value){
        return putInMap(things, destinationMap, new ConstantFunction<K, V>(value));
    }

    /**
     * @author cbongiorno
     *         Date: 7/1/12
     *         Time: 1:22 PM
     */
    private static class DelimitedCollection<T> extends QuickCollection<T> {
        private String delimiter = ",";

        private DelimitedCollection(Collection<T> delegate, String delimiter) {
            super.delegate = delegate;
            this.delimiter = delimiter;
        }


        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            for (T t : delegate)
                b.append(t).append(delimiter);
            b.setLength(Math.max(0,b.length() - delimiter.length())); // remove the extra delimiter
            return b.toString();
        }
    }


    private static class ExceptionOnDuplicateSet<T> implements Set<T> {

        private Set<T> delegate;

        private ExceptionOnDuplicateSet(Set<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        @Override
        public Iterator<T> iterator() {
            return delegate.iterator();
        }

        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return delegate.toArray(a);
        }

        @Override
        public boolean add(T t) {
            if (delegate.contains(t))
                throw new IllegalArgumentException("Duplicate entry attempt. " + t + " already contained");
            return delegate.add(t);
        }

        @Override
        public boolean remove(Object o) {
            return delegate.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            boolean retVal = false;

            for (T t : c)
                retVal |= this.add(t);

            return retVal;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return delegate.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return delegate.removeAll(c);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public String toString() {
            return delegate.toString();
        }

        @Override
        public boolean equals(Object o) {
            return (delegate == o || (delegate != null && delegate.equals(o)));

        }

        @Override
        public int hashCode() {
            return delegate != null ? delegate.hashCode() : 0;
        }
    }

    private static class ExceptionOnDuplicateKeyMap<K, V> extends QuickMap<K,V>{
        private ExceptionOnDuplicateKeyMap(Map<K, V> delegate) {
            super(delegate);
        }

        /**
         * Put a key/value pair into the map, throwing an IllegalArgumentException if the key is already present
         *
         * @param key   the key to attempt to put
         * @param value the value to attempt to put
         * @return always null since no prior key would have existed and thus no value
         */
        public V put(K key, V value) {
            if (delegate.containsKey(key))
                errorMsg(key, value);

            V retVal = delegate.put(key, value);
            if (retVal != null)
                errorMsg(key, value);

            return retVal;
        }

        private V errorMsg(K key, V value) {
            String msg = "Duplicate key " + key + " found.";
            msg += " new val is " + (value == null ? null : value.getClass()) + "(" + value + ")";
            throw new IllegalArgumentException(msg);
        }

        /**
         * @inheritDoc
         */
        public void putAll(Map<? extends K, ? extends V> m) {
            for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
                put(entry.getKey(), entry.getValue());
        }
    }

    private static class DelimitedMap<K, V> extends QuickMap<K,V> {
        private CharSequence preKeyDelim = "";
        private CharSequence postKeyDelim = "";
        private CharSequence postValueDelim = "";
        private boolean trim = false;

        private DelimitedMap(Map<K, V> delegate, CharSequence preKeyDelim, CharSequence postKeyDelim, CharSequence postValueDelim,boolean trim) {
            super(delegate);
            this.delegate = delegate;
            this.preKeyDelim = preKeyDelim;
            this.postKeyDelim = postKeyDelim;
            this.postValueDelim = postValueDelim;
            this.trim = trim;
        }

        private DelimitedMap(Map<K, V> delegate, CharSequence preKeyDelim, CharSequence postKeyDelim, CharSequence postValueDelim) {
            super(delegate);
            this.delegate = delegate;
            this.preKeyDelim = preKeyDelim;
            this.postKeyDelim = postKeyDelim;
            this.postValueDelim = postValueDelim;
        }

        private DelimitedMap(Map<K, V> delegate, CharSequence postKey, CharSequence postValue) {
            super(delegate);
            this.delegate = delegate;
            this.postKeyDelim = postKey;
            this.postValueDelim = postValue;
        }

        public void setTrimming(boolean onOrOff) {
            this.trim = onOrOff;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<K,V> item : entrySet()) {
                sb.append(preKeyDelim);
                sb.append(item.getKey());
                sb.append(postKeyDelim);
                sb.append(item.getValue());
                sb.append(postValueDelim);
            }
            if(trim)
                sb.setLength(Math.max(0,sb.length() - postValueDelim.length())); // remove the extra delimiter

            return sb.toString();
        }
    }

    private static class ExceptionOnDuplicateKeySortedMap<K, V> extends ExceptionOnDuplicateKeyMap<K, V> implements SortedMap<K, V> {
        private SortedMap<K, V> delegate = null;

        private ExceptionOnDuplicateKeySortedMap(SortedMap<K, V> delegate) {
            super(delegate);
            this.delegate = delegate;
        }

        public Comparator<? super K> comparator() {
            return delegate.comparator();
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return delegate.subMap(fromKey, toKey);
        }

        public SortedMap<K, V> headMap(K toKey) {
            return delegate.headMap(toKey);
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return delegate.tailMap(fromKey);
        }

        public K firstKey() {
            return delegate.firstKey();
        }

        public K lastKey() {
            return delegate.lastKey();
        }
    }

    private static class DefaultValueMap<K,V> extends QuickMap<K,V>{
        private Function<Object,V> defaultFunction;

        private DefaultValueMap(Map<K, V> delegate, Function<Object, V> defaultFunction) {
            super(delegate);
            this.defaultFunction = defaultFunction;
        }

        @Override
        public V get(Object key) {
            V retVal = delegate.get(key);
            if( retVal == null && !containsKey(key)){
                retVal = defaultFunction.apply(key);
            }
            return retVal;
        }
    }
}
