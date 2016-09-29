package common.structure;

import common.structure.*;
/**
 * An association that can be compared.
 *
 * @version $Id: ComparableAssociation.java 8 2006-08-02 19:03:11Z bailey $
 * @author, 2001 duane a. bailey
 */
public class ComparableAssociation extends Association
    implements Comparable
{
    /**
     * Construct an association that can be ordered, from only a key.
     * The value is set to null.
     *
     * @pre key is non-null
     * @post constructs comparable association with null value
     *
     * @param key The (comparable) key.
     */
    public ComparableAssociation(Comparable key)
    {
        this(key,null);
    }

    /**
     * Construct a key-value association that can be ordered.
     *
     * @pre key is non-null
     * @post constructs association between a comparable key and a value
     *
     * @param key The (comparable) key.
     * @param value The (possibly comparable) associated value.
     */
    public ComparableAssociation(Comparable key, Object value)
    {
        super(key,value);
    }

    /**
     * Determine the order of two comparable associations, based on key.
     *
     * @pre other is non-null ComparableAssociation
     * @post returns integer representing relation between values
     *
     * @param other The other comparable association.
     * @return Value less-than equal to or greater than zero based on comparison
     */
    public int compareTo(Object other)
    {
        //Assert.pre(other instanceof ComparableAssociation,
         //          "compareTo expects a ComparableAssociation");
        ComparableAssociation that = (ComparableAssociation)other;
        Comparable thisKey = (Comparable)this.getKey();
        Comparable thatKey = (Comparable)that.getKey();

        return thisKey.compareTo(thatKey);
    }

    /**
     * Construct a string representation of the ComparableAssociation.
     *
     * @post returns string representation
     *
     * @return The string representing the ComparableAssociation.
     */
    public String toString()
    {
        StringBuffer s = new StringBuffer();
        s.append("<ComparableAssociation: "+getKey()+"="+getValue()+">");
        return s.toString();
    }
}
