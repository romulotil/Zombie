package br.com.scientists.helper;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHelper extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor accelerometer;
	
	public static float TILT;
	
	
	
	public AccelerometerHelper(Activity activity) {
		 
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
 
        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
 
    }

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		TILT = event.values[1];
		
	}

}
