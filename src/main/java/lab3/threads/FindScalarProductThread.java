package lab3.threads;

import java.util.List;
import java.util.concurrent.Callable;

import lab3.lambda.ScalarProduct;

public record FindScalarProductThread(ScalarProduct scalarProduct, List<Integer> vector1, List<Integer> vector2, int startIndex, int endIndex) implements Callable<Integer> {

    @Override
    public Integer call() {
        return scalarProduct.scalarProduct(vector1, vector2, startIndex, endIndex);
    }

}
