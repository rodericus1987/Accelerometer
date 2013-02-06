package com.example.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Accelerometer1 extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private long lastMeasurement1, lastMeasurement2;
	private final static int FASTEST = 1;
	private final static int NORMAL = 2;
	private int listening;
	private int mode;
	private Sensor mAccelerometer;
	private Sensor mOrientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer1);
		listening = 0;
		lastMeasurement1 = 0;
		lastMeasurement2 = 0;
		mode = NORMAL;
	}

	public void StartStop(View view) {
		if (listening == 1) {
			stopAccelerometer();
		} else {
			startAccelerometer();
		}
	}

	public void StartFastestMode(View view) {
		mode = FASTEST;
		TextView txt3 = (TextView) findViewById(R.id.txt3);
		txt3.setText(R.string.fast_mode);
		if (listening == 1) {
			stopAccelerometer();
			startAccelerometer();
		}
	}

	public void StartNormalMode(View view) {
		mode = NORMAL;
		TextView txt3 = (TextView) findViewById(R.id.txt3);
		txt3.setText(R.string.normal_mode);
		if (listening == 1) {
			stopAccelerometer();
			startAccelerometer();
		}
	}

	public void onSensorChanged(SensorEvent event) {

		if (event.sensor.equals(mAccelerometer)) {
			long timeElapsed = (event.timestamp - lastMeasurement1) / 1000000;
			lastMeasurement1 = event.timestamp;
			TextView textView1 = (TextView) findViewById(R.id.textView1);
			textView1.setText("x: " + event.values[0]);
			TextView textView2 = (TextView) findViewById(R.id.textView2);
			textView2.setText("y: " + event.values[1]);
			TextView textView3 = (TextView) findViewById(R.id.textView3);
			textView3.setText("z: " + event.values[2]);
			TextView txt1 = (TextView) findViewById(R.id.txt1);
			txt1.setText("" + timeElapsed + "ms");
		} else if (event.sensor.equals(mOrientation)) {
			long timeElapsed = (event.timestamp - lastMeasurement2) / 1000000;
			lastMeasurement2 = event.timestamp;
			TextView textView1 = (TextView) findViewById(R.id.textView4);
			textView1.setText("Azimuth: " + event.values[0]);
			TextView textView2 = (TextView) findViewById(R.id.textView5);
			textView2.setText("Pitch: " + event.values[1]);
			TextView textView3 = (TextView) findViewById(R.id.textView6);
			textView3.setText("Roll: " + event.values[2]);
			TextView txt2 = (TextView) findViewById(R.id.txt2);
			txt2.setText("" + timeElapsed + "ms");
		}
	}

	@SuppressWarnings("deprecation")
	public void startAccelerometer() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mOrientation = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (mode == FASTEST) {
			mSensorManager.registerListener(this, mAccelerometer,
					SensorManager.SENSOR_DELAY_FASTEST);
			mSensorManager.registerListener(this, mOrientation,
					SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			mSensorManager.registerListener(this, mAccelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
			mSensorManager.registerListener(this, mOrientation,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		listening = 1;
		lastMeasurement1 = System.nanoTime();
		lastMeasurement2 = System.nanoTime();
	}

	public void stopAccelerometer() {
		mSensorManager.unregisterListener(this);
		listening = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_accelerometer1, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
