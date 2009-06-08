package com.cloak;

import android.app.Activity;
import android.os.Bundle;

public class CloakActivity extends Activity {

    private SampleSurface mSampleSurface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mSampleSurface = new SampleSurface(this);
        setContentView(mSampleSurface);
    }    
    
}