package ru.MrCrashLab.tempAndHumidRecordSystem;

import ru.MrCrashLab.tempAndHumidRecordSystem.Controllers.MicroController;

import java.util.ArrayList;
import java.util.List;

public class ExternalEnvironment {
    public static void main(String[] args) {
        List<Thread> publicThreadList = new ArrayList<>();
        List<MicroController> microControllerList = new ArrayList<>();
        Thread consoleThread;
        ConsoleWorker consoleWorker = new ConsoleWorker();

        microControllerList.add(new MicroController(5000));
        microControllerList.add(new MicroController(7000));
        microControllerList.add(new MicroController(8000));

        for (MicroController controller:microControllerList) {
            publicThreadList.add(new Thread(() -> publicData(controller)));
        }
        for (Thread thread:publicThreadList){
            thread.start();
        }
        consoleThread = new Thread(()->console(consoleWorker, microControllerList));
        consoleThread.start();

        while (true) {
            for(int i = 0; i<publicThreadList.size();i++){
                if(!publicThreadList.get(i).isAlive()){
                    int finalI = i;
                    publicThreadList.set(i, new Thread(() -> publicData(microControllerList.get(finalI))));
                    publicThreadList.get(i).start();
                }
            }
            if(!consoleThread.isAlive()){
                consoleThread = new Thread(()->console(consoleWorker, microControllerList));
                consoleThread.start();
            }
        }
    }

    public static void publicData(MicroController microController) {
        microController.publicData();
    }

    public static void console(ConsoleWorker consoleWorker, List<MicroController> microControllerList){
        consoleWorker.printMicroControllerList(microControllerList);
        consoleWorker.enterIdForChooseController(microControllerList);
    }


}
