package lab2;

import static lab2.CPUSample.goHandle;
import static lab2.CPUSample.sleepRandom;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CPUProcess extends Thread {

    private final List<CPU> cpuList;
    private final CPUQueue queue;
    private final int numberOfIterations;

    public CPUProcess(List<CPU> cpuList, CPUQueue queue, int numberOfIterations) {
        this.cpuList = cpuList;
        this.queue = queue;
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for(int i = 0; i < numberOfIterations; i++) {
            Process process = new Process();
            process.setMessage("Message " + i);
                cpuList.forEach(cpu -> {
                    if(!cpu.equals(cpuList.get(cpuList.size() - 1))){
                        try {
                            if(!process.isSent()) {
                                cpu.getExchanger().exchange(process, 1, TimeUnit.MILLISECONDS);
                                process.setSent(true);
                            }
                            cpu.getExchanger().exchange(process, 1, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (TimeoutException ignored) {}
                    } else {
                        try {
                            if(!process.isSent()) {
                                cpu.getExchanger().exchange(process, 1, TimeUnit.MILLISECONDS);
                                process.setSent(true);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            queue.add(process);
                        }
                    }
                });
            sleepRandom();
        }
        goHandle = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cpuList.forEach(Thread::interrupt);
    }

}
