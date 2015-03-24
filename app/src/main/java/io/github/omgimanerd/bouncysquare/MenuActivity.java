package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends Activity {

  private Button startButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
  }
}
