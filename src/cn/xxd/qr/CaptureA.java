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

package cn.xxd.qr;

import cn.xxd.qr.R;
import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.google.zxing.client.android.FinishListener;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.io.IOException;

import q.util.QLog;

/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureA extends FragmentActivity implements SurfaceHolder.Callback {

  private static final String TAG = CaptureA.class.getSimpleName();

  private CameraManager cameraManager;
  private CaptureActivityHandler handler;
  private ViewfinderView viewfinderView;
  private boolean hasSurface;
  private BeepManager beepManager;
  private CaptureA2 homeA;//TODO add HomeA

  public ViewfinderView getViewfinderView() {
	  QLog.log(this, "getViewfinderView");
    return viewfinderView;
  }

  public Handler getHandler() {
	  //QLog.log(this, "getHandler");
    return handler;
  }

  public CameraManager getCameraManager() {
	  //QLog.log(this, "getCameraManager");
    return cameraManager;
  }

  @Override
  public void onCreate(Bundle icicle) {
	  QLog.log(this, "onCreate");
    super.onCreate(icicle);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    setContentView(R.layout.layout_capture);

    hasSurface = false;
    beepManager = new BeepManager(this);

    homeA = new CaptureA2(this);
    homeA.onCreate();
    
    //
  }
  
  @Override
  protected void onResume() {
	  System.out.println("onResume");
		super.onResume();
		homeA.onResume();
		
		
		cameraManager = new CameraManager(getApplication());
	    
	    //TODO
	    homeA.setCameraManager(cameraManager);

	    viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
	    viewfinderView.setCameraManager(cameraManager);

	    handler = null;

	    SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
	    SurfaceHolder surfaceHolder = surfaceView.getHolder();
	    if (hasSurface) {
	      // The activity was paused but not stopped, so the surface still exists. Therefore
	      // surfaceCreated() won't be called, so init the camera here.
	      initCamera(surfaceHolder);
	    } else {
	      // Install the callback and wait for surfaceCreated() to init the camera.
	      surfaceHolder.addCallback(CaptureA.this);
	      surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

	    beepManager.updatePrefs();
		
	}

  @Override
  protected void onPause() {
	  QLog.log(this, "onPause");
	  super.onPause();
	  homeA.onPause();
	  
	  
	  new Thread(){
			public void run() {
				if (handler != null) {
				      handler.quitSynchronously();
				      handler = null;
				    }
				    cameraManager.closeDriver();
				    /*if (!hasSurface) {
				      SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
				      SurfaceHolder surfaceHolder = surfaceView.getHolder();
				      surfaceHolder.removeCallback(CaptureA.this);
				    }*/
			};
		}.start();
	  //finish();
  }
  
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    case KeyEvent.KEYCODE_MENU:
    	homeA.onKeyDownMenu();
    	return true;
      case KeyEvent.KEYCODE_BACK:
    	  homeA.onKeyDownBack();
    	  return true;
    	  
        /*if (lastResult != null) {
          restartPreviewAfterDelay(0L);
          return true;
        }*/
      case KeyEvent.KEYCODE_FOCUS:
      case KeyEvent.KEYCODE_CAMERA:
        // Handle these events so they don't launch the Camera app
        return true;
      // Use volume up/down to turn on light
      case KeyEvent.KEYCODE_VOLUME_DOWN:
        cameraManager.setTorch(false);
        return true;
      case KeyEvent.KEYCODE_VOLUME_UP:
        cameraManager.setTorch(true);
        return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
	  QLog.log(this, "surfaceCreated");
    if (holder == null) {
      Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
    }
    if (!hasSurface) {
      hasSurface = true;
      initCamera(holder);
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
	  QLog.log(this, "surfaceDestroyed");
    hasSurface = false;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	  QLog.log(this, "surfaceChanged");
  }

  /**
   * A valid barcode has been found, so give an indication of success and show the results.
   *
   * @param rawResult The contents of the barcode.
   * @param barcode   A greyscale bitmap of the camera data which was decoded.
   */
  public void handleDecode(Result rawResult, Bitmap barcode) {
	  QLog.log(this, "handleDecode");
    if (barcode != null) {
      // Then not from history, so beep/vibrate and we have an image to draw on
      beepManager.playBeepSoundAndVibrate();
    }

    homeA.handleDecode(rawResult, barcode);
  }

  private void initCamera(SurfaceHolder surfaceHolder) {
	  QLog.log(this, "initCamera");
    if (surfaceHolder == null) {
      throw new IllegalStateException("No SurfaceHolder provided");
    }
    if (cameraManager.isOpen()) {
      Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
      return;
    }
    try {
      cameraManager.openDriver(surfaceHolder);
      // Creating the handler starts the preview, which can also throw a RuntimeException.
      if (handler == null) {
        handler = new CaptureActivityHandler(this, cameraManager);
      }
    } catch (IOException ioe) {
      Log.w(TAG, ioe);
      displayFrameworkBugMessageAndExit();
    } catch (RuntimeException e) {
      // Barcode Scanner has seen crashes in the wild of this variety:
      // java.?lang.?RuntimeException: Fail to connect to camera service
      Log.w(TAG, "Unexpected error initializing camera", e);
      displayFrameworkBugMessageAndExit();
    }
  }

  private void displayFrameworkBugMessageAndExit() {
	  QLog.log(this, "displayFrameworkBugMessageAndExit");
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(getString(R.string.app_name));
    builder.setMessage(getString(R.string.msg_camera_framework_bug));
    builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
    builder.setOnCancelListener(new FinishListener(this));
    builder.show();
  }

  public void drawViewfinder() {
	  QLog.log(this, "drawViewfinder");
    viewfinderView.drawViewfinder();
  }
}
