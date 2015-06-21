package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.omgimanerd.bouncysquare.customviews.GameView;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.PersistentData;
import io.github.omgimanerd.bouncysquare.util.Util;

public class GameActivity extends Activity implements SensorEventListener {

  private SensorManager sensorManager_;

  private GameView gameView_;
  private TextView liveScoreTextView_;
  private ImageButton pauseButton_;
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

    sensorManager_ = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    setContentView(R.layout.game_layout);

    init();
  }

  private void init() {
    gameView_ = (GameView) findViewById(R.id.gameView);
    liveScoreTextView_= (TextView) findViewById(R.id.liveScoreTextView);
    pauseButton_ = (ImageButton) findViewById(R.id.pauseButton);
    pauseButton_.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gameView_.pause();
        if (gameView_.getPauseState()) {
          pauseButton_.setImageDrawable(
              CustomResources.getDrawable(R.drawable.play));
        } else {
          pauseButton_.setImageDrawable(
              CustomResources.getDrawable(R.drawable.pause));
        }
      }
    });
    lostOverlay_ = (RelativeLayout) findViewById(R.id.lostOverlay);
    lostOverlay_.setVisibility(View.INVISIBLE);
    mainMenuButton_ = (Button) findViewById(R.id.mainMenuButton);
    mainMenuButton_.setVisibility(View.INVISIBLE);
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
    liveScoreTextView_.setText(CustomResources.getString(R.string.score) + score);
  }

  public void showLostOverlay(int score) {
    int highScore = PersistentData.getHighScore();
    if (score > highScore) {
      PersistentData.setHighScore(score);
    }

    pauseButton_.setVisibility(View.INVISIBLE);
    lostOverlay_.setVisibility(View.VISIBLE);
    mainMenuButton_.setVisibility(View.VISIBLE);
    scoreTextView_.setText(CustomResources.getString(R.string.score) + score);
    highscoreTextView_.setText(CustomResources.getString(R.string.highscore) + highScore);
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
      for (int i = 0; i < Util.SCALED_ACCELEROMETER_VALUES.length; ++i) {
        Util.SCALED_ACCELEROMETER_VALUES[i] = event.values[i] *
            PersistentData.getConvertedSensitivity();
      }
    }
  }
}
