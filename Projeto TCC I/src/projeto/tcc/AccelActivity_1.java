package projeto.tcc;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class AccelActivity_1 extends Activity implements SensorEventListener, OnTouchListener {
	TextView textView;
	StringBuilder builder = new StringBuilder();
	private int side;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(new GameView(this, this));
		
		SensorManager manager = (SensorManager)
				getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
			textView.setText("No accelerometer installed");
		} else {
			Sensor accelerometer = manager.getSensorList(
					Sensor.TYPE_ACCELEROMETER).get(0);
			if (!manager.registerListener(this, accelerometer, 
					SensorManager.SENSOR_DELAY_GAME)) {
				textView.setText("Couldn't register sensor listener");
			}
		}
	}
	
	public void onSensorChanged(SensorEvent event) {
		if (event.values[0] * 10 > 0)
			this.side = 1;
		else
			this.side = 2;
	}
	
	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// nothing to do here
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}	
}