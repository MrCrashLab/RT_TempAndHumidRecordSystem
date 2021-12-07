package ru.MrCrashLab.tempAndHumidRecordSystem.Sensors;

public class TempSensor implements SensorInterface{
    private float temperature;

    public TempSensor(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public float getData() {
        return temperature;
    }

    @Override
    public void setData(float data) {
        this.temperature = data;
    }
}
