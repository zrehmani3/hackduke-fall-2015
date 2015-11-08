package com.example.zain.foodbank;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    // protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}