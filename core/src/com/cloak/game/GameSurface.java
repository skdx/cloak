/*
 * Copyright (C) 2009 Jason Tomlinson.
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

package com.cloak.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Custom SurfaceView, provides an easy way for novice developers to avoid getting dirty with Android's SurfaceView.
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
	
	/** Game thread **/
	private SurfaceThread mHeartbeat;
	
	/** Game canvas, where we paint everything **/
	protected Canvas mCanvas;
	/** Game paint, used for colors, font etc.. **/
	protected Paint mPaint;
	/** Custom font **/
	protected Typeface mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
	
	/** Canvas dimensions **/
	protected int mCanvasWidth;
	protected int mCanvasHeight;
	
	/** Used to calculate fps **/
	private long mStartTime = -1;
	private int mCounter;
	private int mFps;
	private final static long FPS_COUNTER_DELAY = 3000;
	
	/** Used to calculate time between game run loop executions **/
	public static long mDelta;
	private static long mLastTickTime = 0;
	private static long mCurrentTickTime = 0;
	
	public GameSurface(Context context) {
		super(context);
		
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(14);
		mPaint.setTypeface(mTypeface);
		
		setFocusable(true);
	    
	}
	
	/** Tick method called by our thread **/
	public void tick() {
	}
	
	/** Draw method called by our thread **/
	public void draw() {
	}
	
	/** Draw frames per second on-screen 
	 * source based on Android official blog http://android-developers.blogspot.com/2009/03/window-backgrounds-ui-speed.html
	 */
	public void drawFPS(int color) {
		if (mStartTime == -1) {
			mStartTime = SystemClock.elapsedRealtime();
			mCounter = 0;
		}

		// only update fps counter every FPS_DELAY, currently 3s
		if (mLastTickTime + FPS_COUNTER_DELAY <= mCurrentTickTime) {
			final long now = SystemClock.elapsedRealtime();
			final long delay = now - mStartTime;
	
			mPaint.setColor(color);
			mCanvas.drawText(mFps + " fps", mCanvasWidth - 50, 10, mPaint);
			
			if (delay > 1000l) {
				mStartTime = now;
				mFps = mCounter;
				mCounter = 0;
			}
			
			mCounter++;
		}
	}
	
	/**
	 * This is called immediately after any structural changes (format or size) have been made to the surface.
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mCanvasWidth = width;
		mCanvasHeight = height;
	}

	/**
	 * This is called immediately after the surface is first created.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		mHeartbeat = new SurfaceThread(holder);
		mHeartbeat.setRunning(true);
		mHeartbeat.start();
	}

	/**
	 * This is called immediately before a surface is being destroyed.
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
	    mHeartbeat.setRunning(false);
        while (retry) {
            try {
            	mHeartbeat.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}
	
	/**
	 * Custom thread used to control game updates and rendering. 
	 */
	public class SurfaceThread extends Thread {
    	private boolean mRunning;
		private SurfaceHolder mSurfaceHolder;
		
		public SurfaceThread(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			mSurfaceHolder.setFormat(PixelFormat.RGB_565);
		}
		
		public void run() {
			while (mRunning) {
				try {
					mCanvas = mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder) {
						mCurrentTickTime = System.currentTimeMillis();
						mDelta = mCurrentTickTime - mLastTickTime;
						tick();
						draw();
						mLastTickTime = mCurrentTickTime;
					}
				}
				finally {
                    if (mCanvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
			}			
		}
		
		public void setRunning(boolean run) {
			mRunning = run;
		}
	}
}
