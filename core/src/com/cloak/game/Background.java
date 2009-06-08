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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Provides a simple mechanism to draw custom backgrounds, these can be static or move in a north, south, east or west direction.
 */
public class Background {
	
	/** Background attributes **/
	private int mX;
	private int mY;
	private int mWidth;
	private int mHeight;
	//TODO should this be done in the same fashion as we animate sprites, movement doesn't appear to be fluid.
	private int mSpeed;
	
	/** Image displayed on-screen **/
	private Bitmap mImage;
	private Paint mPaint;
	
	/** Direction to scroll the background **/ 
	public final static int NONE	= -1;
	public final static int NORTH 	= 1;
	public final static int EAST 	= 2;
	public final static int SOUTH 	= 3;
	public final static int WEST	= 4;
	private int mDirection = NONE;
	
	/** Buffer is used when an image goes off screen, this determines how long until we see the image again **/
	private int mBuffer = 0;
	
	/** Are we displaying a scrolling background **/
	private boolean mParallax;
	
	public Background (int x, int y, int speed, Bitmap img) {
		init(x, y, speed, img, false, NONE, 0);
	}
	
	public Background (int x, int y, int speed, Bitmap img, boolean parallax, int direction, int buffer) {
		init(x, y, speed, img, parallax, direction, buffer);
	}
	
	public void init(int x, int y, int speed, Bitmap img, boolean parallax, int direction, int buffer) {
		mX = x;
		mY = y;
		mSpeed = speed;
		mImage = Bitmap.createBitmap(img);
		mWidth = mImage.getWidth();
		mHeight = mImage.getHeight();
		
		mParallax = parallax;
		
		mDirection = direction;
		mBuffer = buffer;
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}
	
	public void heartbeat() {
		//jmt: move the image based on direction
		if (mDirection > NONE) {
			if (mDirection == NORTH) {
				//TODO: implement north movement
			} else if (mDirection == EAST) {
				//TODO: implement east movement
			} else if (mDirection == SOUTH) {
				//TODO: implement south movement
			} else if (mDirection == WEST) {
				mX -= mSpeed;
			
				if (mX < -mWidth)
					mX = mWidth + mBuffer;
			}
		}
		
	}
	
	public void draw(Canvas canvas) {
		//jmt: draw the image
		canvas.drawBitmap(mImage, mX, mY, mPaint);
		
		//jmt: parallax backgrounds need to be drawn multiple times to produce the required effect of continual motion 
		if (mParallax) {
			if (mDirection == NORTH) {
				//TODO: implement north movement
			} else if (mDirection == EAST) {
				//TODO: implement east movement
			} else if (mDirection == SOUTH) {
				//TODO: implement south movement
			} else if (mDirection == WEST) {
				if (mX < 0)
					canvas.drawBitmap(mImage, mX + mWidth, mY, mPaint);
				else
					canvas.drawBitmap(mImage, mX - mWidth, mY, mPaint);
			}
		}
	}
}
