package com.example.zain.foodbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zain on 11/8/15.
 */
public class LoginActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void loginUser() {
        String foodBankName = ((EditText) findViewById(R.id.food_bank_name)).toString();
        String foodBankPassword = ((EditText) findViewById(R.id.food_bank_password)).toString();
        // check in db
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerUser() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }
}
