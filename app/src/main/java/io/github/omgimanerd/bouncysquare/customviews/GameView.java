package io.github.omgimanerd.bouncysquare.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import io.github.omgimanerd.bouncysquare.game.Game;

import static java.lang.System.currentTimeMillis;

public class GameView extends View implements SensorEventListener {

  private static final int FPS = 60;

  private long lastUpdateTime_;
  private Game game_;
  private SensorManager sensorManager_;

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

    lastUpdateTime_ = currentTimeMillis();
    game_ = new Game();
    sensorManager_ = (SensorManager) getContext().getSystemService(
        Context.SENSOR_SERVICE);
    sensorManager_.registerListener(this, sensorManager_.getDefaultSensor
        (Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
  }

  public void onDraw(Canvas canvas) {
    if (currentTimeMillis() > lastUpdateTime_ + (1000 / FPS)) {
      game_.update();
      game_.render(canvas);
    }

    invalidate();
  }

  public void onAccuracyChanged(Sensor sensor, int value) {}

  public void onSensorChanged(SensorEvent event) {
    game_.onOrientationChange(event.values);
  }

  public boolean onTouchEvent(MotionEvent event) {
    game_.onTouch(event);
    return true;
  }
}
