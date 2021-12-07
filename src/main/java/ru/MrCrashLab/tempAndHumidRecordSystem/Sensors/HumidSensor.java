package ru.MrCrashLab.tempAndHumidRecordSystem.Sensors;

public class HumidSensor implements SensorInterface{
    private float humid;

    public HumidSensor(float humid) {
        this.humid = humid;
    }

    @Override
    public float getData() {
        return humid;
    }

    @Override
    public void setData(float data) {
        this.humid = data;
    }
}
