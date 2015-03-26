package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class SettingsActivity extends Activity {

  private SharedPreferences persistentData_;

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
    persistentData_ = getSharedPreferences("bouncy_square", 0);
    int sensitivity = persistentData_.getInt("sensitivity", 2);

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
        SharedPreferences.Editor editor = persistentData_.edit();
        editor.putInt("sensitivity", seekBar.getProgress());
        editor.commit();
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
