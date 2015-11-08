package com.example.zain.foodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends Activity {

    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getSharedPreferences("user", MODE_PRIVATE).contains("email")) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            finish();
        } else if (!getSharedPreferences("user", MODE_PRIVATE).getBoolean("has_profiled", false)) {
            Intent intent = new Intent(this, CompleteProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String regId = GCMUtils.getRegistrationId(this);
        if (regId.equals("")) {
            GCMUtils.registerInBackground(getApplicationContext());
        }
    }

}