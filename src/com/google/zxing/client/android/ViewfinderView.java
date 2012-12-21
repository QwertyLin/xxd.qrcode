/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import cn.xxd.qr.util.QConfig;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {
	
  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 80L;
  private static final int CURRENT_POINT_OPACITY = 0xA0;
  private static final int MAX_RESULT_POINTS = 20;
  private static final int POINT_SIZE = 6;

  private CameraManager cameraManager;
  private final Paint paint;
  private final int laserColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private List<ResultPoint> possibleResultPoints;
  private List<ResultPoint> lastPossibleResultPoints;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    getResources();
    //resultColor = resources.getColor(R.color.result_view);
    laserColor = QConfig.VIEW_FINDER_LASER_COLOR;//laserColor = resources.getColor(R.color.viewfinder_laser);
    resultPointColor = 0xc0ffbd21;
    scannerAlpha = 0;
    possibleResultPoints = new ArrayList<ResultPoint>(5);
    lastPossibleResultPoints = null;
  }

  public void setCameraManager(CameraManager cameraManager) {
    this.cameraManager = cameraManager;
  }

  @Override
  public void onDraw(Canvas canvas) {
	  //System.out.println("onDraw" + Calendar.getInstance().getTimeInMillis());
    if (cameraManager == null) {
      return; // not ready yet, early draw before done configuring
    }
    Rect frame = cameraManager.getFramingRect();
    if (frame == null) {
      return;
    }
    
    // Draw the exterior (i.e. outside the framing rect) darkened
    /*
    int width = canvas.getWidth();
    int height = canvas.getHeight();
    paint.setColor(maskColor);
    canvas.drawRect(0, 0, width, frame.top, paint);
    canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
    canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
    canvas.drawRect(0, frame.bottom, width, height, paint);*/
    
    //Draw frame
    int third = (frame.right - frame.left) / 3;
    int weight = 5;
    paint.setColor(0xFF0099CC);
    canvas.drawRect(frame.left, frame.bottom - weight, frame.left + third, frame.bottom, paint);
    canvas.drawRect(frame.left, frame.bottom - third, frame.left + weight, frame.bottom, paint);
    paint.setColor(0xFF669900);
    canvas.drawRect(frame.left, frame.top, frame.left + third, frame.top + weight, paint);
    canvas.drawRect(frame.left, frame.top, frame.left + weight, frame.top + third, paint);
    paint.setColor(0xFFCC0000);
    canvas.drawRect(frame.right - third, frame.top, frame.right, frame.top + weight, paint);
    canvas.drawRect(frame.right - weight, frame.top, frame.right, frame.top + third, paint);
    paint.setColor(0xFFFF8800);
    canvas.drawRect(frame.right - third, frame.bottom - weight, frame.right, frame.bottom, paint);
    canvas.drawRect(frame.right - weight, frame.bottom - third, frame.right, frame.bottom, paint);

      // Draw a red "laser scanner" line through the middle to show decoding is active
      paint.setColor(laserColor);
      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
      
      //TODO draw激光 
      //int middle = frame.width() / 2 + frame.left;//int middle = frame.height() / 2 + frame.top;
      //canvas.drawRect(middle - 1, frame.top + 2, middle + 2, frame.bottom - 2, paint);//canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
      
      Rect previewFrame = cameraManager.getFramingRectInPreview();
      float scaleX = frame.width() / (float) previewFrame.width();
      float scaleY = frame.height() / (float) previewFrame.height();

      List<ResultPoint> currentPossible = possibleResultPoints;
      List<ResultPoint> currentLast = lastPossibleResultPoints;
      int frameLeft = frame.left;
      int frameTop = frame.top;
      if (currentPossible.isEmpty()) {
        lastPossibleResultPoints = null;
      } else {
        possibleResultPoints = new ArrayList<ResultPoint>(5);
        lastPossibleResultPoints = currentPossible;
        paint.setAlpha(CURRENT_POINT_OPACITY);
        paint.setColor(resultPointColor);
        synchronized (currentPossible) {
          for (ResultPoint point : currentPossible) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              POINT_SIZE, paint);
          }
        }
      }
      if (currentLast != null) {
        paint.setAlpha(CURRENT_POINT_OPACITY / 2);
        paint.setColor(resultPointColor);
        synchronized (currentLast) {
          float radius = POINT_SIZE / 2.0f;
          for (ResultPoint point : currentLast) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              radius, paint);
          }
        }
      }

      // Request another update at the animation interval, but only repaint the laser line,
      // not the entire viewfinder mask.
      postInvalidateDelayed(ANIMATION_DELAY,
                            frame.left - POINT_SIZE,
                            frame.top - POINT_SIZE,
                            frame.right + POINT_SIZE,
                            frame.bottom + POINT_SIZE);
  }

  public void drawViewfinder() {
    invalidate();
  }

  public void addPossibleResultPoint(ResultPoint point) {
    List<ResultPoint> points = possibleResultPoints;
    synchronized (points) {
      points.add(point);
      int size = points.size();
      if (size > MAX_RESULT_POINTS) {
        // trim it
        points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
      }
    }
  }

}
