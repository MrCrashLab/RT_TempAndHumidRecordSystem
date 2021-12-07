package ru.MrCrashLab.tempAndHumidRecordSystem;

import ru.MrCrashLab.tempAndHumidRecordSystem.Controllers.MicroController;

import java.util.List;
import java.util.Scanner;

public class ConsoleWorker {
    private final Scanner scanner = new Scanner(System.in);
    private final String errorMessage = "\u001B[91mВы ввели некоректное значение!\u001B[0m";

    public void printMicroControllerList(List<MicroController> microControllerList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID\t\tControllerID\tCurrentTemp\t\tCurrentHumid\n");
        for (int i = 0; i < microControllerList.size(); i++) {
            stringBuilder.append('[');
            stringBuilder.append(i);
            stringBuilder.append("] \t");
            stringBuilder.append('[');
            stringBuilder.append(microControllerList.get(i).getId());
            stringBuilder.append("] \t");
            if (microControllerList.get(i).getId() < 100000000)
                stringBuilder.append("\t");
            stringBuilder.append(microControllerList.get(i).getTempSensor().getData());
            stringBuilder.append("\t\t\t");
            if (microControllerList.get(i).getTempSensor().getData() < 10 && microControllerList.get(i).getTempSensor().getData() >= 0)
                stringBuilder.append("\t");
            stringBuilder.append(microControllerList.get(i).getHumidSensor().getData());
            stringBuilder.append("\t");
            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder);
    }

    public void enterIdForChooseController(List<MicroController> microControllerList) {
        int id;
        int mode;
        float temp;
        float humid;
        while (true) {
            System.out.print("Введите ID контроллера(первая колонка), в которой вы хотите передать значения: ");
            id = scanner.nextInt();
            if (id >= microControllerList.size() || id < 0) {
                System.out.println(errorMessage);
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Выберите какое значение вы хотите изменить(1 - температура, 2 - влажность): ");
            mode = scanner.nextInt();
            if (mode != 1 && mode != 2) {
                System.out.println(errorMessage);
            } else {
                break;
            }
        }
        while (true) {
            if (mode == 1) {
                System.out.print("Введите новое значение температуры (Диапазон от -20 до +60): ");
                temp = scanner.nextInt();
                if (temp < -20 || temp > 60) {
                    System.out.println(errorMessage);
                } else {
                    microControllerList.get(id).getTempSensor().setData(temp);
                    break;
                }
            } else if (mode == 2) {
                System.out.print("Введите новое значение влажности (Диапазон от 0 до 99): ");
                humid = scanner.nextInt();
                if (humid < 0 || humid > 99) {
                    System.out.println(errorMessage);
                } else {
                    microControllerList.get(id).getHumidSensor().setData(humid);
                    break;
                }
            }
        }
    }
}
