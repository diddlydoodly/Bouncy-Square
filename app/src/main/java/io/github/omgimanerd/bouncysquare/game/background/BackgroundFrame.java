package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class BackgroundFrame {

  private RectF trueFrame_;
  private RectF mappedFrame_;
  private Paint bgPaint_;

  public BackgroundFrame(float left, float top, float right, float bottom) {
    trueFrame_ = new RectF(left, top, right, bottom);
    bgPaint_ = new Paint();
    bgPaint_.setColor(Color.WHITE);
  }

  public static BackgroundFrame generateRandomFrame(float left, float top,
                                             float right, float bottom) {
    return new BackgroundFrame(left, top, right, bottom);
  }

  public void update(ViewPort viewPort) {
    mappedFrame_ = viewPort.mapToCanvas(trueFrame_);
  }

  public void redraw(Canvas canvas) {
    canvas.drawRect(mappedFrame_, bgPaint_);
  }

  public RectF getTrueFrame() {
    return trueFrame_;
  }
}
