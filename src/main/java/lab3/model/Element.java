package lab3.model;

import java.util.StringJoiner;

public class Element implements Comparable<Element> {
    private long element;
    private int elementIndex;

    public long getElement() {
        return element;
    }

    public void setElement(long element) {
        this.element = element;
    }

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    @Override
    public int compareTo(Element o) {
        if(element > o.element) {
            return 1;
        } else if(element < o.element) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Element.class.getSimpleName() + "[", "]").add("element=" + element).add("elementIndex=" + elementIndex)
                .toString();
    }
}
