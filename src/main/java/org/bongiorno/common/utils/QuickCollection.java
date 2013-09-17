package org.bongiorno.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author cbongiorno
 *         Date: 4/24/12
 *         Time: 3:11 PM
 *
 *         Allows you to quickly expose a wrapper class as a collection (and thus gain benefits) by
 *         merely setting the underlying collection object
 *
 *         Usage note: if you intend to use this with an XML marshalled object it will go much easier if you use property
 *         marshalling or NONE and explicitly call out the marshaling so you can set the super.delegate in the setter
 *         method containing the delegate.
 *         Field marshalling will not give you a single injection point
 *         to guarantee that the child is fully setup and also does not give the parent 'this' any opportunity to
 *         query for the delegate. In addition, overriding the default contructor and setting the delegate to something
 *         you intend to use  can also help. Different marshaling strategies treat Collections differently than Pojos
 *         and attempt to iterate and add to them.   Beware of CXF!
 */

public class QuickCollection<T> implements Collection<T> {

    protected Collection<T> delegate;

    public QuickCollection() {
        //To behave like a normal collection, we set it to something safe.
        this.delegate = new LinkedList<>();
    }

    public QuickCollection(Collection<T> delegate) {
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
        return delegate.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public String toString() {
        return "" + delegate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuickCollection that = (QuickCollection) o;

        if (delegate != null ? !delegate.equals(that.delegate) : that.delegate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return delegate != null ? delegate.hashCode() : 0;
    }
}
