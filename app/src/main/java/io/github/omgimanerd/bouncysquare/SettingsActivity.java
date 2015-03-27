package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import io.github.omgimanerd.bouncysquare.util.PersistentData;

public class SettingsActivity extends Activity {

  private SeekBar sensitivityControl_;
  private Button mainMenuButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.settings_layout);

    init();
  }

  private void init() {
    int sensitivity = PersistentData.getRawSensitivity();

    sensitivityControl_ = (SeekBar) findViewById(R.id.sensitivityControl);
    sensitivityControl_.setProgress(sensitivity);
    sensitivityControl_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
                                    boolean fromUser) {}

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {}

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        PersistentData.setSensitivity(seekBar.getProgress());
      }
    });

    mainMenuButton_ = (Button) findViewById(R.id.mainMenuButton);
    mainMenuButton_.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),
                                   MenuActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

}
