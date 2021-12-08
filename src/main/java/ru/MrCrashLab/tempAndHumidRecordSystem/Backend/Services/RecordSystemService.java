package ru.MrCrashLab.tempAndHumidRecordSystem.Backend.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.MrCrashLab.tempAndHumidRecordSystem.Backend.Models.RecordTemperatureHumidityModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordSystemService {
    @Value("${db.URL}")
    private String dbUrl;
    @Value("${db.Login}")
    private String dbLogin;
    @Value("${db.Password}")
    private String dbPassword;

    public List<RecordTemperatureHumidityModel> getAllRecordList() {
        List<RecordTemperatureHumidityModel> recordSystemList = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM temp_and_humid_data");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                RecordTemperatureHumidityModel model = new RecordTemperatureHumidityModel(
                        result.getInt("id"),
                        result.getInt("id_controller"),
                        result.getDouble("temperature"),
                        result.getDouble("humid"),
                        result.getTimestamp("time")
                );
                recordSystemList.add(model);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return recordSystemList;
    }


    public List<RecordTemperatureHumidityModel> getRecordListByIdController(int idController) {
        List<RecordTemperatureHumidityModel> recordSystemListById = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM temp_and_humid_data WHERE id_controller=?");
            preparedStatement.setInt(1, idController);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                RecordTemperatureHumidityModel model = new RecordTemperatureHumidityModel(
                        result.getInt("id"),
                        result.getInt("id_controller"),
                        result.getDouble("temperature"),
                        result.getDouble("humid"),
                        result.getTimestamp("time")
                );
                recordSystemListById.add(model);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return recordSystemListById;
    }
}
