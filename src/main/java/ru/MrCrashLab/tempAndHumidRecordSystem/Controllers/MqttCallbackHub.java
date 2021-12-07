package ru.MrCrashLab.tempAndHumidRecordSystem.Controllers;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MqttCallbackHub implements MqttCallback {
    private final String dbUrl;
    private final String dbLogin;
    private final String dbPassword;
    private final Map<String, Map<String,Float>> data;

    public MqttCallbackHub(String dbUrl,
                           String dbLogin,
                           String dbPassword,
                           Map<String, Map<String,Float>> data) {
        this.dbUrl = dbUrl;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
        this.data = data;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.println(s + " " + new String(mqttMessage.getPayload()));
        if (data.get(s.split("/")[0]) == null)
            data.put(s.split("/")[0], new HashMap<>());
        if (s.split("/")[1].equals("temperature"))
            data.get(s.split("/")[0]).put("temperature",Float.parseFloat(new String(mqttMessage.getPayload())));
        else if (s.split("/")[1].equals("humidity"))
            data.get(s.split("/")[0]).put("humidity",Float.parseFloat(new String(mqttMessage.getPayload())));

        if (data.get(s.split("/")[0]) != null &&
                data.get(s.split("/")[0]).get("temperature") != null &&
                data.get(s.split("/")[0]).get("humidity") != null) {
            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO temp_and_humid_data " +
                                "(id_controller," +
                                " temperature," +
                                " humid," +
                                " time) " +
                                "VALUES (?,?,?,?)");
                preparedStatement.setInt(1, Integer.parseInt(s.split("/")[0].split("_")[1]));
                preparedStatement.setFloat(2, data.get(s.split("/")[0]).get("temperature"));
                preparedStatement.setFloat(3, data.get(s.split("/")[0]).get("humidity"));
                preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                preparedStatement.execute();
                connection.close();
                data.remove(s.split("/")[0]);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Delivery complete");
    }
}
