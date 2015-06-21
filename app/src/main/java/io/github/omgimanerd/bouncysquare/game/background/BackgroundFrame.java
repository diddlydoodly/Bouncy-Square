package io.github.omgimanerd.bouncysquare.game.background;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.game.ViewPort;
import io.github.omgimanerd.bouncysquare.util.CustomResources;

public class BackgroundFrame {

  private static final float NEBULA_CHANCE = 0.25f;

  private RectF trueFrame_;
  private RectF mappedFrame_;
  private Paint bgPaint_;
  private int starSize_;

  public BackgroundFrame(float left, float top, float right, float bottom,
                         int starSize) {
    trueFrame_ = new RectF(left, top, right, bottom);
    bgPaint_ = new Paint();
    bgPaint_.setColor(Color.BLACK);
    starSize_ = starSize;
  }

  public static BackgroundFrame generateRandomFrame(float left, float top,
                                             float right, float bottom) {
    int starSize = (int) Math.floor(
        CustomResources.BG_STARS.length * Math.random());
    return new BackgroundFrame(left, top, right, bottom, starSize);
  }

  public void update(ViewPort viewPort) {
    mappedFrame_ = viewPort.mapToCanvas(trueFrame_);
  }

  public void redraw(Canvas canvas) {
    canvas.drawRect(mappedFrame_, bgPaint_);
    canvas.drawBitmap(CustomResources.BG_STARS[starSize_], null,
                      mappedFrame_, null);
  }

  public RectF getTrueFrame() {
    return trueFrame_;
  }
}
