package io.github.omgimanerd.bouncysquare.game.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;

public class BackgroundFrame {

  private RectF trueFrame_;
  private RectF mappedFrame_;
  private Bitmap bgImage_;

  public BackgroundFrame(float left, float top, float right, float bottom, Bitmap bgImage) {
    trueFrame_ = new RectF(left, top, right, bottom);
    bgImage_ = bgImage;
  }

  public void update(ViewPort viewPort) {
    mappedFrame_ = viewPort.mapToCanvas(trueFrame_);
  }

  public void redraw(Canvas canvas) {
    if (bgImage_ != null) {
      canvas.drawBitmap(bgImage_, null, mappedFrame_, null);
    }
  }

  public RectF getTrueFrame() {
    return trueFrame_;
  }
}
