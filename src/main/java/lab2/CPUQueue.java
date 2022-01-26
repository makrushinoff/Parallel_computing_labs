package lab2;

import java.util.LinkedList;
import java.util.Queue;

public class CPUQueue {

    private Queue<Process> queue = new LinkedList<>();
    private int maxQueueSize = 0;

    public synchronized void add(Process process) {
        queue.add(process);

        System.out.println("Add process: " + process + " to queue");
        if(queue.size() > maxQueueSize) {
            maxQueueSize = queue.size();
        }
        notifyAll();
    }

    public synchronized Process get() throws InterruptedException {
        if(queue.isEmpty()) {
            System.out.println("Queue is empty!");
            wait();
        }
        Process process = queue.remove();
        System.out.println("Return process: " + process);
        return process;
    }

    public synchronized Queue<Process> getQueue() {
        return queue;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }
}
