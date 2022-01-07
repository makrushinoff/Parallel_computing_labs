package lab2;

import java.util.Arrays;
import java.util.Random;

public class CPUSample {

    public static volatile boolean goHandle = true;

    public static void main(String[] args) throws InterruptedException {
        Process process = new Process();

        CPU cpu1 = new CPU(process);
        CPU cpu2 = new CPU(process);
        CPUProcess cpuProcess = new CPUProcess(Math.abs(new Random().nextInt() % 100 + 10), process, Arrays.asList(cpu1, cpu2));
        System.out.println("Inputed: " + cpuProcess.getGeneralNumberOfProcesses());
        cpu1.start();
        cpu2.start();
        cpuProcess.start();

        cpuProcess.join();
        cpu1.join();
        cpu2.join();

        int handled = cpu1.getHandledProcesses() + cpu2.getHandledProcesses();
        int destroyed = cpuProcess.getGeneralNumberOfProcesses() - handled;
        double percentOfDestroyed = ((double)destroyed / (double)cpuProcess.getGeneralNumberOfProcesses()) * 100;
        System.out.println("Destroyed: " + destroyed + " processes. It is a " + percentOfDestroyed + "% of all processes");
    }
}
