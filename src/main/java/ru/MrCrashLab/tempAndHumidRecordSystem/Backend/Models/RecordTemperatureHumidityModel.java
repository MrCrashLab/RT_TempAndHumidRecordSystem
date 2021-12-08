package ru.MrCrashLab.tempAndHumidRecordSystem.Backend.Models;

import java.sql.Timestamp;

public class RecordTemperatureHumidityModel {
    private int id;
    private int id_controller;
    private double temperature;
    private double humid;
    private Timestamp time;

    public RecordTemperatureHumidityModel(int id,
                                          int id_controller,
                                          double temperature,
                                          double humid,
                                          Timestamp time) {
        this.id = id;
        this.id_controller = id_controller;
        this.temperature = temperature;
        this.humid = humid;
        this.time = time;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_controller() {
        return id_controller;
    }

    public void setId_controller(int id_controller) {
        this.id_controller = id_controller;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


}
