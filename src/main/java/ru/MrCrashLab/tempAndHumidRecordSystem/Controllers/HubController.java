package ru.MrCrashLab.tempAndHumidRecordSystem.Controllers;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HubController {
    private final String dbUrl;
    private final String dbLogin;
    private final String dbPassword;
    private final List<String> topicNames;
    private Map<String, Float[]> data = new HashMap<>();
    private Float[] tmp = new Float[2];

    public HubController(List<String> topicNames) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File("src/main/resources/hub_controller.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbUrl = properties.getProperty("db.URL");
        dbLogin = properties.getProperty("db.Login");
        dbPassword = properties.getProperty("db.Password");
        this.topicNames = topicNames;
    }

    public void subscribeData() {
        System.out.println("\u001B[34mStart Subscriber\u001B[0m");
        try {
            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.setCallback(new MqttCallbackHub(dbUrl, dbLogin, dbPassword, data, tmp));
            client.connect();
            client.subscribe(topicNames.stream().toArray(String[]::new));
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
