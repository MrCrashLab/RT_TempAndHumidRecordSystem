package ru.MrCrashLab.tempAndHumidRecordSystem.Controllers;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.*;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;

public class MqttCallbackHub implements MqttCallback {
    private final String dbUrl;
    private final String dbLogin;
    private final String dbPassword;
    private final Map<String, Float[]> data;
    private final Float tmp[];

    public MqttCallbackHub(String dbUrl,
                           String dbLogin,
                           String dbPassword,
                           Map<String, Float[]> data,
                           Float tmp[]) {
        this.dbUrl = dbUrl;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
        this.data = data;
        this.tmp = tmp;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.println(s + " " + new String(mqttMessage.getPayload()));
        if (data.get(s.split("/")[0]) == null)
            data.put(s.split("/")[0], new Float[2]);
        if (s.split("/")[1].equals("temperature"))
            tmp[0] = Float.parseFloat(new String(mqttMessage.getPayload()));
        else if (s.split("/")[1].equals("humidity"))
            tmp[1] = Float.parseFloat(new String(mqttMessage.getPayload()));

        if (data.get(s.split("/")[0]) != null &&
                tmp[0] != null &&
                tmp[1] != null) {
            data.put(s.split("/")[0], tmp);
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
                preparedStatement.setFloat(2, data.get(s.split("/")[0])[0]);
                preparedStatement.setFloat(3, data.get(s.split("/")[0])[1]);
                preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                preparedStatement.execute();
                connection.close();
                tmp[0] = null;
                tmp[1] = null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Delivery complete");
    }
}
