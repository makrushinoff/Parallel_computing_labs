package lab3.lambda;

import java.util.List;

@FunctionalInterface
public interface ScalarProduct {

    int scalarProduct(List<Integer> firstVector, List<Integer> secondVector, int startIndex, int endIndex);

}
