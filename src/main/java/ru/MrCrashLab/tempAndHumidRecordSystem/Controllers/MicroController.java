package ru.MrCrashLab.tempAndHumidRecordSystem.Controllers;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ru.MrCrashLab.tempAndHumidRecordSystem.Sensors.HumidSensor;
import ru.MrCrashLab.tempAndHumidRecordSystem.Sensors.SensorInterface;
import ru.MrCrashLab.tempAndHumidRecordSystem.Sensors.TempSensor;

import java.util.Map;

public class MicroController {
    private final int id; //уникальный номер микроконтроллера
    private final int timeToPublish; //время через которое публикуются значения в топике
    private final HumidSensor humidSensor;
    private final String controllerName;
    private final TempSensor tempSensor;
    private final Map<String, SensorInterface> sensors;

    public MicroController(int timeToPublish) {
        id = this.hashCode();
        this.timeToPublish = timeToPublish;
        humidSensor = new HumidSensor(0);
        tempSensor = new TempSensor(0);
        sensors = Map.of("temperature", tempSensor,
                "humidity", humidSensor);
        controllerName = "Controller_" + id + '/';
    }

    public void publicData() {
        String topicName;
        try {
            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.connect();
            for (Map.Entry<String, SensorInterface> entry : sensors.entrySet()) {
                topicName = controllerName + entry.getKey();
                MqttMessage message = new MqttMessage();
                message.setPayload(Double.toString(entry.getValue().getData()).getBytes());
                client.publish(topicName, message);

            }
            Thread.sleep(timeToPublish);
            client.disconnect();
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public HumidSensor getHumidSensor() {
        return humidSensor;
    }

    public TempSensor getTempSensor() {
        return tempSensor;
    }

    public Map<String, SensorInterface> getSensors() {
        return sensors;
    }

    public int getId() {
        return id;
    }
}
