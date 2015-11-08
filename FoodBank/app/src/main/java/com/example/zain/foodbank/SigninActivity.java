package com.example.zain.foodbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zain on 11/7/15.
 */
public class SigninActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        final String foodBankName = ((EditText) findViewById(R.id.food_bank_name)).toString();
        final String foodBankAddress = ((EditText) findViewById(R.id.food_bank_address)).toString();
        final String foodBankPassword = ((EditText) findViewById(R.id.food_bank_password)).toString();

        boolean hasError = false;

        if (foodBankName.length() == 0) {
            ((EditText) findViewById(R.id.food_bank_name)).setError(getText(R.string.name_empty));
            hasError = true;
        } else {
            ((EditText) findViewById(R.id.food_bank_name)).setError(null);
        }
        if (foodBankPassword.length() == 0) {
            ((EditText) findViewById(R.id.food_bank_password)).setError(getText(R.string.email_invalid));
            hasError = true;
        } else {
            ((EditText) findViewById(R.id.food_bank_password)).setError(null);
        }

        if (!hasError) {
            findViewById(R.id.register_button).setEnabled(false);
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
