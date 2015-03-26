package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.omgimanerd.bouncysquare.customviews.GameView;
import io.github.omgimanerd.bouncysquare.util.Colors;
import io.github.omgimanerd.bouncysquare.util.SensorValues;
import io.github.omgimanerd.bouncysquare.util.Util;

public class GameActivity extends Activity implements SensorEventListener {

  private Resources res_;
  private SensorManager sensorManager_;

  private TextView liveScoreTextView_;
  private RelativeLayout lostOverlay_;
  private Button mainMenuButton_;
  private TextView scoreTextView_;
  private TextView highscoreTextView_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.game_layout);

    init();
  }

  private void init() {
    res_ = getResources();
    Util.SCREEN_WIDTH = res_.getDisplayMetrics().widthPixels;
    Util.SCREEN_HEIGHT = res_.getDisplayMetrics().heightPixels;
    Colors.STANDARD_COLORS[0] = res_.getColor(R.color.STANDARD_RED);
    Colors.STANDARD_COLORS[1] = res_.getColor(R.color.STANDARD_BLUE);
    Colors.STANDARD_COLORS[2] = res_.getColor(R.color.STANDARD_GREEN);
    Colors.STANDARD_COLORS[3] = res_.getColor(R.color.STANDARD_YELLOW);

    sensorManager_ = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    liveScoreTextView_= (TextView) findViewById(R.id.liveScoreTextView);
    lostOverlay_ = (RelativeLayout) findViewById(R.id.lostOverlay);
    mainMenuButton_ = (Button) findViewById(R.id.mainMenuButton);
    mainMenuButton_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        backToMenu();
      }
    });
    scoreTextView_ = (TextView) findViewById(R.id.scoreTextView);
    highscoreTextView_ = (TextView) findViewById(R.id.highscoreTextView);
  }

  private void backToMenu() {
    Intent intent = new Intent(getApplicationContext(),
                               MenuActivity.class);
    startActivity(intent);
    overridePendingTransition(R.anim.abc_slide_in_bottom,
                              R.anim.abc_slide_out_top);
    finish();
  }

  public void updateScoreView(int score) {
    liveScoreTextView_.setText(res_.getString(R.string.score) + score);
  }

  public void showLostOverlay(int score, int highscore) {
    lostOverlay_.setVisibility(View.VISIBLE);
    scoreTextView_.setText(res_.getString(R.string.score) + score);
    highscoreTextView_.setText(res_.getString(R.string.highscore) + highscore);
  }

  protected void onResume() {
    sensorManager_.registerListener(
        this,
        sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_FASTEST);
    super.onResume();
  }

  protected void onPause() {
    sensorManager_.unregisterListener(this);
    backToMenu();
    super.onStop();
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
