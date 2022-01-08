package lab3.threads;

import java.util.concurrent.Callable;

import lab3.lambda.FindElement;
import lab3.model.Element;

public record FindElementThread(FindElement findElement, long[] array, int startIndex, int endIndex) implements Callable<Element> {

    @Override
    public Element call() {
        return findElement.find(array, startIndex, endIndex);
    }
}
