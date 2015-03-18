package io.github.omgimanerd.bouncysquare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

  private Button startButton_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.menu_layout);

    init();
  }

  public void init() {
    startButton_ = (Button) findViewById(R.id.startButton);
    startButton_.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setContentView(R.layout.game_layout);
      }
    });
  }
}
