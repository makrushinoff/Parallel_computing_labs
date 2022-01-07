package lab2;

import static lab2.CPUSample.goHandle;

import java.util.List;

public class CPUProcess extends Thread {

    public int generalNumberOfProcesses;
    public int requestedProcesses = 0;
    private final Process process;
    private final List<CPU> cpuList;

    public CPUProcess(int generalNumberOfProcesses, Process process, List<CPU> cpuList) {
        this.generalNumberOfProcesses = generalNumberOfProcesses;
        this.process = process;
        this.cpuList = cpuList;
    }

    @Override
    public void run() {
        while(requestedProcesses <= generalNumberOfProcesses) {
            System.out.println("Want to set message " + requestedProcesses);
            synchronized (process) {
                process.setMessage("Message " + requestedProcesses);
                requestedProcesses++;
                process.setNeedToHandle(true);
                process.notify();
            }
        }
        goHandle = false;
        System.out.println("Process thread goes sleep");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cpuList.forEach(cpu -> {
            if(!cpu.getState().equals(State.TERMINATED)) {
                System.out.println("Interrupting " + cpu.getName());
                cpu.interrupt();
            }
        });
    }

    public int getGeneralNumberOfProcesses() {
        return generalNumberOfProcesses;
    }
}
