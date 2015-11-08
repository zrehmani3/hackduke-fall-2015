package com.example.zain.foodbank;
import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.call_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallButtonClicked();
            }
        });
    }

    private void onCallButtonClicked() {
        Intent intent = new Intent(MainActivity.this, SymptomsActivity.class);
        startActivity(intent);
        finish();
    }
}