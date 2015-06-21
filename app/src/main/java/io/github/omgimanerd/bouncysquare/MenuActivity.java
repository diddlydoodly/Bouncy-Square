package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import io.github.omgimanerd.bouncysquare.util.CustomResources;
import io.github.omgimanerd.bouncysquare.util.PersistentData;
import io.github.omgimanerd.bouncysquare.util.Util;

public class MenuActivity extends Activity {

  private Button startButton_;
  private Button settingsButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // This initialization must take place before the setting of the layout.
    CustomResources.init(this);
    PersistentData.init(this);
    Util.init(this);

    setContentView(R.layout.menu_layout);

    init();
  }

  private void init() {
    startButton_ = (Button) findViewById(R.id.startButton);
    startButton_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),
                                   GameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_slide_in_top,
                                  R.anim.abc_slide_out_bottom);
        finish();
      }
    });

    settingsButton_ = (Button) findViewById(R.id.settingsButton);
    settingsButton_.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),
                                   SettingsActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }
}
