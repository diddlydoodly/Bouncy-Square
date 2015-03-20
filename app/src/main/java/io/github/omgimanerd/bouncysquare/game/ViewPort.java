package io.github.omgimanerd.bouncysquare.game;

import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.util.Util;

/**
 * Created by omgimanerd on 3/18/15.
 * ViewPort's coordinates are absolute coordinates and not canvas coordinates.
 * ViewPort will map given coordinates to the canvas.
 */
public class ViewPort {

  private float minY_;
  private float upperScrollBound_;

  public ViewPort() {
    minY_ = 0;
    upperScrollBound_ = Util.SCREEN_HEIGHT / 3 * 4;
  }

  public void update(Square square) {
    if (square.getSquare().top > upperScrollBound_) {
      minY_ = square.getSquare().top - Util.SCREEN_WIDTH / 3 * 4;
    }
  }

  /**
   * Maps the given RectF to it's position relative to the canvas so that it
   * can be drawn.
   * @param rect
   * @return The appropriately mapped RectF
   */
  public RectF mapToCanvas(RectF rect) {
    RectF mappedRectF = new RectF(rect);
    mappedRectF.offsetTo(rect.left, Util.SCREEN_HEIGHT - (rect.top - minY_));
    return mappedRectF;
  }
}
