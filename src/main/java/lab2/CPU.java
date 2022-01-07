package lab2;

import static lab2.CPUSample.goHandle;

public class CPU extends Thread {

    private int handledProcesses = 0;
    private final Process process;

    public CPU(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        while(goHandle) {
            synchronized (process) {
                try {
                    System.out.println(this.getName() + " is waiting");
                    while(!process.isNeedToHandle()) {
                        try {
                            process.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Force stopped " + getName());
                            return;
                        }
                    }
                    process.setNeedToHandle(false);
                    System.out.println(this.getName() + "Got message: " + process.getMessage());
                    Thread.sleep((long) (Math.random() * 1000 + 50));
                    handledProcesses++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(getName() + " goes sleep");
    }

    public int getHandledProcesses() {
        return handledProcesses;
    }

}
