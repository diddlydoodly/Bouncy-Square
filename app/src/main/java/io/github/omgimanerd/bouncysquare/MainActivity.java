package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import io.github.omgimanerd.bouncysquare.customviews.GameView;
import io.github.omgimanerd.bouncysquare.util.Util;

public class MainActivity extends Activity {

  private Button startButton_;
  private GameView gameView_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.menu_layout);

    Util.SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
    Util.SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;

    init();
  }

  /**
  public void onResume() {
    if (gameView_ != null) {
      gameView_.registerSensorListener();
    }
    super.onResume();
  }

  public void onPause() {
    if (gameView_ != null) {
      gameView_.unregisterSensorListener();
    }
    super.onPause();
  }*/

  public void init() {
    startButton_ = (Button) findViewById(R.id.startButton);
    startButton_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setContentView(R.layout.game_layout);
      }
    });

    gameView_ = (GameView) findViewById(R.id.gameView);
  }
}
