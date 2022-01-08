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
        for (int i = startIndex; i <= endIndex; i++) {
            result += Math.pow(Math.abs(array.get(i)), 2);
        }
    }

    public double getResult() {
        return result;
    }
}
