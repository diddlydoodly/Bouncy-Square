package io.github.omgimanerd.bouncysquare.game;

import android.graphics.RectF;

import io.github.omgimanerd.bouncysquare.util.Util;

/**
 * Created by omgimanerd on 3/18/15.
 * ViewPort's coordinates are absolute coordinates and not canvas coordinates.
 * ViewPort will map given coordinates to the canvas.
 */
public class ViewPort {

  private RectF viewPort_;
  private float upperScrollBound_;

  public ViewPort() {
    viewPort_ = new RectF(0, Util.SCREEN_HEIGHT, Util.SCREEN_WIDTH, 0);
    upperScrollBound_ = Util.SCREEN_HEIGHT / 3 * 4;
  }

  public void update(Square square) {
    if (square.getSquare().top > upperScrollBound_) {
      viewPort_.offsetTo(0, square.getSquare().top + Util.SCREEN_HEIGHT / 4);
    }
  }

  public float getTop() {
    return viewPort_.top;
  }

  public float getBottom() {
    return viewPort_.bottom;
  }

  /**
   * Maps the given RectF to it's position relative to the canvas so that it
   * can be drawn.
   * @param rect
   * @return The appropriately mapped RectF
   */
  public RectF mapToCanvas(RectF rect) {
    RectF mappedRectF = new RectF(rect);
    mappedRectF.offsetTo(rect.left, Util.SCREEN_HEIGHT - (rect.top -
        viewPort_.bottom));
    return mappedRectF;
  }

  /**
   * Returns if the given RectF is visible inside of the current viewport
   * bounds.
   * @param rect
   * @return
   */
  public boolean isVisible(RectF rect) {
    return viewPort_.intersect(rect);
  }
}
