package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class BackgroundFrame {

  private RectF trueFrame_;
  private RectF mappedFrame_;
  private Bitmap backgroundBitmap_;

  public BackgroundFrame(float left, float top, float right, float bottom,
                         Bitmap backgroundBitmap) {
    trueFrame_ = new RectF(left, top, right, bottom);
    backgroundBitmap_ = backgroundBitmap;
  }

  public void update(ViewPort viewPort) {
    mappedFrame_ = viewPort.mapToCanvas(trueFrame_);
  }

  public void redraw(Canvas canvas) {
    canvas.drawBitmap(backgroundBitmap_, null, mappedFrame_, null);
  }

  public RectF getTrueFrame() {
    return trueFrame_;
  }
}
