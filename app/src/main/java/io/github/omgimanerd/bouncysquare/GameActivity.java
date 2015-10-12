package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import io.github.omgimanerd.bouncysquare.customviews.GameView;
import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.PersistentData;
import io.github.omgimanerd.bouncysquare.util.Util;

public class GameActivity extends Activity implements SensorEventListener {

  private SensorManager sensorManager_;

  private GameView gameView_;
  private TextView liveScoreTextView_;

  private ImageButton settingsButton_;
  private ImageButton pauseButton_;

  private RelativeLayout settingsOverlay_;
  private SeekBar sensitivityControl_;

  private RelativeLayout lostOverlay_;
  private TextView scoreTextView_;
  private TextView highscoreTextView_;
  private Button playAgainButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    sensorManager_ = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    Util.init(this);
    CustomResources.init(this);
    PersistentData.init(this);

    setContentView(R.layout.game_layout);

    init();
  }

  private void init() {
    gameView_ = (GameView) findViewById(R.id.gameView);
    liveScoreTextView_= (TextView) findViewById(R.id.liveScoreTextView);
    settingsOverlay_ = (RelativeLayout) findViewById(R.id.settingsOverlay);
    settingsOverlay_.setVisibility(View.INVISIBLE);
    lostOverlay_ = (RelativeLayout) findViewById(R.id.lostOverlay);
    lostOverlay_.setVisibility(View.INVISIBLE);

    settingsButton_ = (ImageButton) findViewById(R.id.settingsButton);
    settingsButton_.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gameView_.pause();
        if (settingsOverlay_.getVisibility() == View.VISIBLE) {
          settingsButton_.setImageDrawable(CustomResources.getDrawable(R.drawable.settings));
          settingsOverlay_.setVisibility(View.INVISIBLE);
        } else {
          settingsButton_.setImageDrawable(CustomResources.getDrawable(R.drawable.close));
          settingsOverlay_.setVisibility(View.VISIBLE);
        }
      }
    });
    sensitivityControl_ = (SeekBar) findViewById(R.id.sensitivityControl);
    sensitivityControl_.setProgress(PersistentData.getRawSensitivity());
    sensitivityControl_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {}

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        PersistentData.setSensitivity(seekBar.getProgress());
      }
    });

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

    playAgainButton_ = (Button) findViewById(R.id.playAgainButton);
    playAgainButton_.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gameView_.reset();
        lostOverlay_.setVisibility(View.INVISIBLE);
      }
    });
    scoreTextView_ = (TextView) findViewById(R.id.scoreTextView);
    highscoreTextView_ = (TextView) findViewById(R.id.highscoreTextView);
  }

  public void updateScoreView(int score) {
    liveScoreTextView_.setText(CustomResources.getString(R.string.score) + score);
  }

  public void showLostOverlay(int score) {
    int highScore = PersistentData.getHighScore();
    if (score > highScore) {
      PersistentData.setHighScore(score);
    }

    lostOverlay_.setVisibility(View.VISIBLE);
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
    super.onPause();
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    Log.d("accuracy log", "Accuracy: " + accuracy);
  }

  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      Util.ACCELEROMETER_TILT = -event.values[0];
    }
  }
}
