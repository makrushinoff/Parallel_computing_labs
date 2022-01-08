package lab3.lambda;

import lab3.model.Element;

@FunctionalInterface
public interface FindElement {

    Element find(long[] array, int startIndex, int endIndex);

}
