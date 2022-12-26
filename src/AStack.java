public class AStack<E> implements ADTStack<E> {
    private static final int defaultSize = 10;

    private int maxSize;
    private int top;
    private E [] listArray;

    /** Constructors */
    public AStack() { this(defaultSize); }

    public
    AStack(int size) {
        maxSize = size;
        top = 0;
        listArray = (E[])new Object[size];   // Create listArray
    }

    /** Reinitialize stack */
    public void clear() { top = 0; }

    /** Push "it" onto stack */
    public void push(E it) {
        assert top != maxSize : "Stack is full";
        // for our purposes for this assignment, we need ensure pushed element is unique
        for(int i = 0; i < this.length(); i++)
        {
            if(it.equals(listArray[i]))
                return;
        }
        listArray[top] = it;
        ++top;
    }

    /** Remove and top element */
    public E pop() {
        assert top != 0 : "Stack is empty";
        E value = listArray[--top];

        return value;
    }

    /** @return Top element */
    public E topValue() {
        assert top != 0 : "Stack is empty";
        return listArray[top-1];
    }

    /** @return Stack size */
    public int length() { return top; }

    public String toString()
    {
        StringBuffer str = new StringBuffer((length() + 1) * 4);
        str.append("< ");
        for (int i = top-1; i >= 0; i--) {
            str.append(listArray[i]);
            str.append(" ");
        }
        str.append(">");
        return str.toString();
    }
}