package com.cloak;

import com.cloak.game.GameSurface;
import com.cloak.game.Sprite;

import android.content.Context;
import android.graphics.BitmapFactory;

public class SampleSurface extends GameSurface {

    Sprite mSpriteRobot;
        
    public SampleSurface(Context context) {
        super(context);
        mSpriteRobot = new Sprite(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.android_wrench), 0, 0);
    }

    @Override
    public void draw() {
        mSpriteRobot.draw(mCanvas);
    }
}
