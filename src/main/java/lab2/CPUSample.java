package lab2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class CPUSample {

    public static boolean goHandle = true;

    public static void sleepRandom() {
        try {
            Thread.sleep(Math.abs(new Random().nextInt()) % 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print("Input number of iterations: ");
        int iterations = new Scanner(System.in).nextInt();
        CPUQueue queue = new CPUQueue();
        Exchanger<Process> exchanger1 = new Exchanger<>();
        Exchanger<Process> exchanger2 = new Exchanger<>();
        CPU cpu1 = new CPU(queue, exchanger1);
        CPU cpu2 = new CPU(queue, exchanger2);
        CPUProcess cpuProcess = new CPUProcess(Arrays.asList(cpu1, cpu2), queue, iterations);
        final List<Thread> threads = List.of(cpu1, cpu2, cpuProcess);
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Max queue size: " + queue.getMaxQueueSize());
    }
}
