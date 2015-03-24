package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Arrays;

import io.github.omgimanerd.bouncysquare.util.SensorValues;
import io.github.omgimanerd.bouncysquare.util.Util;

public class GameActivity extends Activity implements SensorEventListener {

  private SensorManager sensorManager_;

  private RelativeLayout lostOverlay_;
  private Button lostOverlayButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    Util.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
    Util.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;

    setContentView(R.layout.game_layout);

    init();
  }

  public void init() {
    sensorManager_ = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    lostOverlay_ = (RelativeLayout) findViewById(R.id.lostOverlay);
    lostOverlayButton_ = (Button) findViewById(R.id.lostOverlayButton);
    lostOverlayButton_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),
                                   MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_slide_in_bottom,
                                  R.anim.abc_slide_out_top);
        finish();
      }
    });
  }

  public void showLostOverlay() {
    lostOverlay_.setVisibility(View.VISIBLE);
  }

  public void onResume() {
    sensorManager_.registerListener(
        this,
        sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_FASTEST);
    super.onResume();
  }

  public void onPause() {
    sensorManager_.unregisterListener(this);
    super.onPause();
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {}

  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      for (int i = 0; i < SensorValues.ACCELEROMETER_VALUES.length; ++i) {
        SensorValues.ACCELEROMETER_VALUES[i] = event.values[i] *
            SensorValues.SENSITIVITY;
      }
    }
  }
}
