package common.linearstructure;

import common.structure.*;

abstract public class AbstractLinear extends AbstractStructure
    implements Linear
{
    /**
     * Determine if there are elements within the linear.
     *
     * @post return true iff the linear structure is empty
     * @return true if the linear structure is empty; false otherwise
     */
    public boolean empty()
    {
        return isEmpty();
    }

    /**
     * Removes value from the linear structure.
     * Not implemented (by default) for linear classes.
     *
     * @param value value matching the value to be removed
     * @pre value is non-null
     * @post value is removed from linear structure, if it was there
     * @return returns the value that was replaced, or null if none.
     */
    public Object remove(Object o)
    {
        //Assert.fail("Method not implemented.");
        // never reaches this statement:
        return null;
    }
}
