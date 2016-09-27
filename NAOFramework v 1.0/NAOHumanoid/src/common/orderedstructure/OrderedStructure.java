package common.orderedstructure;

import common.structure.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
/**
 * An interface the supports a Collection whose values are kept
 * in increasing order.  Values stored within ordered structures
 * should implement Comparable; ie. they should have an implemented
 * lessThan method.
 *
 * @see java.lang.Comparable
 * @see java.lang.Comparable#compareTo
 * @version $Id: OrderedStructure.java 8 2006-08-02 19:03:11Z bailey $
 * @author, 2001 duane a. bailey
 */
public interface OrderedStructure extends Structure
{
}
