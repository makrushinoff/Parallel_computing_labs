package lab1;

import java.util.List;

public class ThreadCalc extends Thread {

    private List<Double> array;
    private int startIndex;
    private int endIndex;

    private double result = 0.0;

    public ThreadCalc(List<Double> array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        double maxValue = 0.0;
        for (int i = startIndex; i <= endIndex; i++) {
            if(maxValue < Math.abs(array.get(i))) {
                maxValue = array.get(i);
            }
        }
        result = maxValue;
    }

    public double getResult() {
        return result;
    }
}
