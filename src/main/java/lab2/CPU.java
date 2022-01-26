package lab2;

import static lab2.CPUSample.goHandle;
import static lab2.CPUSample.sleepRandom;

import java.util.concurrent.Exchanger;

public class CPU extends Thread {

    private final CPUQueue queue;
    private final Exchanger<Process> exchanger;

    public CPU(CPUQueue queue, Exchanger<Process> exchanger) {
        this.queue = queue;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while(goHandle) {
            try {
                Process message;
                if(queue.getQueue().isEmpty()) {
                    message = exchanger.exchange(null);
                } else {
                    message = queue.get();
                }
                System.out.println(getName() + "Got message: " + message);
                sleepRandom();
            } catch (InterruptedException e) {
                System.out.println(getName() + " is interrupted");
            }
        }

    }

    public Exchanger<Process> getExchanger() {
        return exchanger;
    }
}
