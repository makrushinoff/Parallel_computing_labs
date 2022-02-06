package lab3;

import java.util.concurrent.Callable;

public class ThreadCalc implements Callable<Long> {

    private final int[] array;
    private final int startIndex;
    private final int endIndex;

    public ThreadCalc(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;
        for(int i = startIndex; i <= endIndex; i++) {
            if(array[i] % 10 == 0){
                sum += array[i];
            }
        }
        return sum;
    }
}
