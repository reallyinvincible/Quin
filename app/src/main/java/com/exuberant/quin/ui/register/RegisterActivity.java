package com.exuberant.quin.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.exuberant.quin.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private String email = "", password = "", name = "";
    private TextInputLayout emailTextInputLayout, nameTextInputLayout, passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize() {

        emailTextInputLayout = findViewById(R.id.tl_email);

        Intent intent = getIntent();
        if (intent.hasExtra("email")){
            email = intent.getStringExtra("email");
        }
        if (intent.hasExtra("password")){
            password = intent.getStringExtra("password");
        }
        email = "mailtosparshsri@gmail.com";
        bindUi();
    }

    private void bindUi() {
        if (email != "" && email.length() > 0){
            emailTextInputLayout.getEditText().setText(email);
            emailTextInputLayout.getEditText().setEnabled(false);
        }
    }
}
