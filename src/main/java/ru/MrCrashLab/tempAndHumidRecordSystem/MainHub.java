package ru.MrCrashLab.tempAndHumidRecordSystem;

import ru.MrCrashLab.tempAndHumidRecordSystem.Controllers.HubController;

import java.util.ArrayList;
import java.util.List;

public class MainHub {
    public static void main(String[] args) {
        List<String> topicNames = new ArrayList<>();
        topicNames.add("Controller_947679291/temperature");
        topicNames.add("Controller_947679291/humidity");
        topicNames.add("Controller_846063400/temperature");
        topicNames.add("Controller_846063400/humidity");
        topicNames.add("Controller_627150481/temperature");
        topicNames.add("Controller_627150481/humidity");
        HubController hubController = new HubController(topicNames);
        hubController.subscribeData();
    }
}
