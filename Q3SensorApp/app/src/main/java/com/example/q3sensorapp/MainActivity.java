package com.example.q3sensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerometer, light, proximity;

    TextView accelerometerText, lightText, proximityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometerText = findViewById(R.id.accelerometerText);
        lightText = findViewById(R.id.lightText);
        proximityText = findViewById(R.id.proximityText);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, light,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, proximity,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerText.setText(
                    "Accelerometer\n" +
                            "X: " + event.values[0] + "\n" +
                            "Y: " + event.values[1] + "\n" +
                            "Z: " + event.values[2]
            );
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightText.setText(
                    "Light\n" +
                            "Value: " + event.values[0]
            );
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityText.setText(
                    "Proximity\n" +
                            "Value: " + event.values[0]
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}