package common.set;


import java.util.Iterator;
import common.structure.*;
import singlylinkedlist.*;

/**
 * Implementation of a set of elements.
 * As with the mathematical object, the elements of the set are
 * not duplicated.  No order is implied or enforced in this structure.
 *
 * @version $Id: AbstractSet.java 8 2006-08-02 19:03:11Z bailey $
 * @author, 2001 duane a. bailey
 */
public abstract class AbstractSet extends AbstractStructure implements Set
{
    /**
     * Union other set into this set.
     * @pre other is non-null
     * @post values from other are added into this set
     */
    public void addAll(Structure other)
    {
        Iterator i = other.iterator();
        while (i.hasNext())
        {
            add(i.next());
        }
    }

    /**
     * Check to see if this set is contained in the other structure.
     * @pre other is non-null
     * @post returns true if every value in this set is contained in the
     *       other
     */
    public boolean containsAll(Structure other)
    {
        Iterator i = other.iterator();
        while (i.hasNext())
        {
            if (!contains(i.next())) return false;
        }
        return true;
    }

    /**
     * Computes the difference between this set and the other structure
     * @pre other is non-null
     * @post values of this set contained in other are removed
     */
    public void removeAll(Structure other)
    {
        Iterator i = other.iterator();
        while (i.hasNext())
        {
            remove(i.next());
        }
    }

    /**
     * Computes the intersection between this set and the other structure.
     * @pre other is non-null
     * @post values not appearing in the other structure are removed
     */
    public void retainAll(Structure other)
    {
        List drop = new SinglyLinkedList();
        Iterator i = other.iterator();
        while (i.hasNext())
        {
            Object o = i.next();
            if (!other.contains(o)) drop.add(o);
        }
        while (!drop.isEmpty())
        {
            remove(drop.removeFirst());
        }
    }
}
