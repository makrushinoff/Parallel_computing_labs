package lab1;

public class ThreadCalc extends Thread {

    private final int[][] matrix;
    private final int startIndex;
    private final int endIndex;

    public ThreadCalc(int[][] matrix, int startIndex, int endIndex) {
        this.matrix = matrix;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
            int rowSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != matrix[i][i]) {
                    rowSum += matrix[i][j];
                }
            }
            matrix[i][i] = rowSum;
        }
    }

}