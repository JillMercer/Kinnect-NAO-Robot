package common.structure;

import java.util.Iterator;

public class ArrayIterator extends AbstractIterator
{
    protected Object data[];
    protected int head, count;
    protected int current, remaining;

    public ArrayIterator(Object source[])
    {
        this(source,0,source.length);
    }

    public ArrayIterator(Object source[], int first, int size)
    {
        data = source;
        head = first;
        count = size;
        reset();
    }

    public void reset()
    {
        current = head;
        remaining = count;
    }

    public boolean hasNext()
    {
        return remaining > 0;
    }

    public Object next()
    {
        Object temp = data[current];
        current = (current+1)%data.length;
        remaining--;
        return temp;
    }

    public Object get()
    {
        return data[current];
    }
}
