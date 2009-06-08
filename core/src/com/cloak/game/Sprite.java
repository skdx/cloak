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
import android.graphics.RectF;

/**
 * Custom sprite class useful for game development in Android.
 */
public class Sprite {

	/** Sprite attributes **/
	protected int mX;
	protected int mY;
	protected int mWidth;
	protected int mHeight;
	/** Action sprite may be performing **/
	protected int mAction;
	public final static int ACTION_VOID = -1;
	
	/** Sprite's paint object **/
	protected final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	/** Sprite's rectangle, used for collisions **/
	protected RectF mCollisionRect;
	
	/** Sprite bitmap **/
	protected Bitmap mSprite;
	
	/** Is the Sprite currently visible ie. should we paint it **/
	protected boolean mVisible;

	/** Used to rotate the image **/
	protected float mRotateDegrees;
	
	//TODO: clipping
	private int mClipX;
	private int mClipY;
	private int mClipWidth;
	private int mClipHeight;
	
	public Sprite() {
		init(null, 0, 0, false);
	}
	
	public Sprite(Bitmap img) {
		init(img, 0, 0, false);
		
	}
	
	public Sprite(Bitmap img, int x, int y) {
		init(img, x, y, true);
	}
	
	public void init(Bitmap img, int x, int y, boolean visible) {
		mX = x;
		mY = y;
		
		if (img != null) {
			mSprite = img;
			mWidth = img.getWidth();
			mHeight = img.getHeight();
		} else {
			mSprite = null;
			mWidth = -1;
			mHeight = -1;
		}
		mCollisionRect = new RectF(mX, mY, mX + mWidth, mY + mHeight);
		
		mClipX = mX;
		mClipY = mY;
		mClipWidth = mWidth;
		mClipHeight = mHeight;
		
		mVisible = visible;
		mAction = ACTION_VOID;
		
		mRotateDegrees = 0;
	}
	
	/** Sprite draw routine **/
	public void draw(Canvas canvas) {
		if (mVisible) {
			canvas.save();
			canvas.rotate(mRotateDegrees, mX + mWidth/2, mY + mHeight/2);
			canvas.drawBitmap(mSprite, mX, mY, mPaint);
			canvas.restore();
		}
	}
	
	/** Return x position **/
	public int getX() {
		return mX;
	}
	
	/** Return y position **/
	public int getY() {
		return mY;
	}
	
	/** Return width **/
	public int getWidth() {
		return mWidth;
	}
	
	/** Return height **/
	public int getHeight() {
		return mHeight;
	}
	
	/** Return sprite's collision rectangle **/
	public RectF getRectF() {
		return mCollisionRect;
	}
	
	/** Return sprite's current action **/
	public int getAction() {
		return mAction;
	}
	
	/** Set the sprite's action **/
	public void setAction(int action) {
		mAction = action;
	}
	
	/** Set the sprite's x position **/
	public void setX(int x) {
		mX = x;
	}
	
	/** Set the sprite's y position **/
	public void setY(int y) {
		mY = y;
	}
	
	/** Set a new x,y co-ordinate for the sprite **/
	public void setPosition(int x, int y) {
		mX = x;
		mY = y;
	}
	
	/** Increment the sprite's x position **/
	public void updateX(int x) {
		mX += x;
	}
	
	/** Increment the sprite's y position **/
	public void updateY(int y) {
		mY += y;
	}
	
	/** Set the sprite's width **/
	public void setWidth(int w) {
		mWidth = w;
	}
	
	/** Set the sprite's height **/
	public void setHeight(int h) {
		mHeight = h;
	}
	
	public void setDimensions(int w, int h) {
		mWidth = w;
		mHeight = h;
	}
	
	public void rotate(float angle) {
		mRotateDegrees = angle;
	}
	
	public void incrementAngle(float increment) {
		mRotateDegrees += increment;
	}
	
	/** Set the sprite's visibility **/
	public void setVisible(boolean visible) {
		mVisible = visible;
	}
	
	/** Is the sprite current visible i.e. is it being drawn? **/
	public boolean isVisible() {
		return mVisible;
	}

	/** Update the collision rect with the sprite's last position **/
	public void updateRect() {
		mCollisionRect.left = mX;
		mCollisionRect.top = mY;
		mCollisionRect.right = mX + mWidth;
		mCollisionRect.bottom = mY + mHeight;
	}
	
	/** Does x,y intersect the sprite's current location **/
	public boolean isTouched(int x, int y) {
		updateRect();
		return mCollisionRect.contains(x, y);
	}
}
