package io.github.omgimanerd.bouncysquare.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {



  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);

  }

  public void onDraw(Canvas canvas) {

  }

  public boolean onTouchEvent(MotionEvent event) {
    return true;
  }
}
