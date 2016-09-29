package common.linearstructure;


/**
 * An abstract structure describing a First-In, First-Out structure.
 * Queues are typically used to store the state of a buffered object.
 *
 * @version $Id: AbstractQueue.java 8 2006-08-02 19:03:11Z bailey $
 * @author, 2001 duane a. bailey
 */
public abstract class AbstractQueue
    extends AbstractLinear implements Queue
{
    /**
     * Add a value to the tail of the queue.
     *
     * @post the value is added to the tail of the structure
     *
     * @param value The value added.
     */
    public void enqueue(Object item)
    {
        add(item);
    }

    /**
     * Remove a value form the head of the queue.
     *
     * @pre the queue is not empty
     * @post the head of the queue is removed and returned
     *
     * @return The value actually removed.
     */
    public Object dequeue()
    {
        return remove();
    }

    /**
     * Fetch the value at the head of the queue.
     *
     * @pre the queue is not empty
     * @post the element at the head of the queue is returned
     *
     * @return Reference to the first value of the queue.
     */
    public Object getFirst()
    {
        return get();
    }

    /**
     * Fetch the value at the head of the queue.
     *
     * @pre the queue is not empty
     * @post the element at the head of the queue is returned
     *
     * @return Reference to the first value of the queue.
     */
    public Object peek()
    {
        return get();
    }
}
